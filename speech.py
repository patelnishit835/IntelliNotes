import speech_recognition as sr
import sys
from pydub import AudioSegment
from pydub.utils import make_chunks
import time
import requests

r = sr.Recognizer()
filename = sys.argv[1]
answer = "";

myaudio = AudioSegment.from_file(filename , "wav") 
chunk_length_ms = 10000 # pydub calculates in millisec
chunks = make_chunks(myaudio, chunk_length_ms) #Make chunks of ten sec

#Export all of the individual chunks as wav files

for i, chunk in enumerate(chunks):
	chunk_name = "chunk{0}.wav".format(i)
	print ("exporting", chunk_name)
	chunk.export(chunk_name, format="wav")
	time.sleep(0.01)
	with sr.AudioFile(chunk_name) as source:
		audio = r.listen(source)
	try:
		a = r.recognize_google(audio)
		#print("Predicted :"+a)
		answer += a
		#answer += ".";
	except Exception:
		print("Something is wrong!")
print(answer)

"""r = requests.post(
		"https://api.deepai.org/api/summarization",

		data = {
			'text': answer
		},
		headers={'api-key': '9fc27a08-f0db-4ccd-a745-ad7267a2489b'}
	)

print(r.json())"""