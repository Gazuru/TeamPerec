OBJS	= main.o
SOURCE	= main.cpp
HEADER	= Caff.h CaffAnimation.h CaffCredits.h CaffHeader.h Ciff.h MyCustomException.h Util.h gif.h
OUT	= caff_parser
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 = 

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp 


clean:
	rm -f $(OBJS) $(OUT)
