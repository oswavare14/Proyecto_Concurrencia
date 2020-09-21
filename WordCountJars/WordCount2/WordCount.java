import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

    public static class MAPPER extends Mapper<Object, Text, Text, IntWritable> {

        IntWritable one = new IntWritable(1);
        Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] palabras = value.toString().split("\\s+");
            String str1 = null;
            String str2;
            if (palabras.length != 0) {
                str1 = palabras[0];
            }
            for (int i = 1; i < palabras.length; i++) {
                str2 = palabras[i];
                word.set(str1 + " , " + str2);
                context.write(word, one);
                str1 = str2;
            }
        }
    }

    public static class REDUCER extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int suma = 0;
            for (IntWritable val : values) {
                suma += val.get();
            }
            result.set(suma);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "wordcount double");
        job.setJarByClass(WordCount.class);

        job.setMapperClass(MAPPER.class);
        job.setCombinerClass(REDUCER.class);
        job.setReducerClass(REDUCER.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}