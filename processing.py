
fl = open("OneWordoutput.txt", encoding="utf8")
output = open("CleanText.txt","w", encoding="utf8")




flag = 0
validToken = False

for token in fl:
    text_token = token.split()
    validToken = True if len(text_token) > 1 else False
    if validToken:
        word = text_token[0]
        count = text_token[1]
        if (flag ==1 and count.isdecimal()): 
            x= int(count)
            if x >= 5000:
                line = word + "\t" + count + "\n"
                output.write(line)
            flag = 0
        else:
            flag = 1
    else:
        flag = 0
 
output.close()
fl.close()