from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize, sent_tokenize
import sys

file = open('data.txt', 'r')

stopWords = set(stopwords.words("english"))

text = file.read()

freqTable = dict()
# for word in words:
#     word = word.lower()
#     if word in stopWords:
#         continue
#     if word in freqTable:
#         freqTable[word] += 1
#     else:
#         freqTable[word] = 1

# print(freqTable)




#sentences = sent_tokenize(text)
sentences = text.strip().split('.')
sentenceValue = dict()

for words in sentences:
    for word in words:
        word = word.lower()
        if word in stopWords:
            continue
        if word in freqTable:
            freqTable[word] += 1
        else:
            freqTable[word] = 1



# for sentence in sentences:
#     for wordValue in freqTable:
#         if wordValue in sentence.lower():
#         	print(wordValue)

for sentence in sentences:
    for wordValue in freqTable:
        if wordValue in sentence.lower():
            if sentence in sentenceValue:
                sentenceValue[sentence] += 1
            else:
                sentenceValue[sentence] = 1

# for sentence in sentences:
#     sentence = sentence.lower() 
#     if sentence in sentenceValue:
#         sentenceValue[sentence] += 1
#     else:
#         sentenceValue[sentence] = 1

#print(sentenceValue)

sumValues = 0
for sentence in sentenceValue:
    sumValues += sentenceValue[sentence]

# Average value of a sentence from original text
average = int(sumValues/ len(sentenceValue))


summary = ''
for sentence in sentences:
        if sentence in sentenceValue and sentenceValue[sentence] > (1.1 * average):
            summary +=  " " + sentence

# print(summary)
print(summary)
file.close()
# file1 = open('summary.txt', 'w')
# file1.write(summary)
# file1.close()

