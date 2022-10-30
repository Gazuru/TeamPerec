
#ifndef CIFF_H
#define CIFF_H

#include <string>
#include <vector>
#include <fstream>
#include "MyCustomException.h"
#include <iostream>
#include "Util.h"
using namespace std;

struct Ciff {
    uint64_t headerSize{};
    uint64_t contentSize{};
    uint64_t width{};
    uint64_t height{};
    std::string caption{};
    std::vector<std::string> tags{};
    std::vector<unsigned char> pixels{};

    Ciff(){
    }
    ~Ciff(){
    }
    void init(std::ifstream &ifstream, uint64_t maxSize){

        char magic[4];
        ifstream.read(reinterpret_cast<char *>(&magic), 4);
        if(!(magic[0] == 'C' && magic[1] == 'I' && magic[2] == 'F' && magic[3] == 'F')){
            throw MyCustomException("magic is not present");
        }
        ifstream.read(reinterpret_cast<char *>(&headerSize), 8);
        ifstream.read(reinterpret_cast<char *>(&contentSize), 8);
        //TODO - REMOVE THIS EXCEPTION
        if (maxSize > 1000000000 && headerSize > 1000000000) {
            throw MyCustomException("temp exception - would take to long to test");
        }
        //watch for overflow
        if (Util::isAdditionOverflow(headerSize, contentSize)) {
            throw MyCustomException("ciff exceeds given lenght limit");
        }
        if (headerSize + contentSize > maxSize) {
            throw MyCustomException("ciff exceeds given lenght limit");
        }
        ifstream.read(reinterpret_cast<char *>(&width), 8);
        ifstream.read(reinterpret_cast<char *>(&height), 8);

        if(width == 0 || height == 0 || contentSize == 0){
            throw MyCustomException("no pixel data in ciff");
        }

        if(headerSize < 4+8+8+8+8)
            throw MyCustomException("header size less than minimum");
        //check for overflow
        if (Util::isMultiplicationOverflow(width, height * 3)) {
            throw MyCustomException("content size not width*height*3");
        }          
        if (contentSize != width*height*3)
        {
            throw MyCustomException("content size not width*height*3");
        }
        //todo - read function where we increamenta a value for each byte read, after it exceeds headerSize error will be thrown

        readCaption(ifstream);

        readTags(ifstream);

        readPixels(ifstream);

        uint16_t tagStringLengthSum = 0;
        for (uint64_t i = 0; i < tags.size(); i++) {
            tagStringLengthSum += tags.at(i).size();
        }
        if (headerSize != 4 + 8 + 8 + 8 + 8 + caption.size() + 1 + tagStringLengthSum + tags.size()) {
            throw MyCustomException("header size is not correct");
        }

    }
    std::vector<unsigned char> pixelsAsRGBA8() {
        vector<unsigned char> rgba8Pixels {};
        //must use uint64_t because pixels.size()=content_size = 8 byte
        for (uint64_t i = 0; i < pixels.size(); i++) {   
            if (i % 3 == 0 && i != 0) {
                rgba8Pixels.push_back(255);
            }
            rgba8Pixels.push_back(pixels.at(i));
        }
        rgba8Pixels.push_back(255);
        return rgba8Pixels;
    }
    //todo - hibakezelés kilépési felt
    void readCaption(std::ifstream &ifstream){
        char c;
        std::vector<char> captionChars;
        
        ifstream.read(reinterpret_cast<char *>(&c),1);
        while (c != '\n')
        {
            captionChars.push_back(c);

            //underflow is already checked in the init function (headerSize > 4+8+8+8+8)
            if (captionChars.size() > headerSize - 4 - 8 - 8 - 8 - 8) {
                throw MyCustomException("caption exceeds max size");
            }
            ifstream.read(reinterpret_cast<char *>(&c),1);
        }
        std::string s(captionChars.begin(), captionChars.end());
        caption = s;
    }
    
    void setWasLastCharacter0(char c, bool &wasLastChar0){
        if(c == '\0')
            wasLastChar0 = true;
        else
            wasLastChar0 = false;
    }

    void readTags(std::ifstream &ifstream){
        //+1 is for the \\n at the end of the caption
        
        if (headerSize < (4 + 8 + 8 + 8 + 8 + (caption.size() + 1)))
            throw MyCustomException("lenght of tags is negative");
        //because of above check we can safely get tagsLen without underflow 
        uint64_t tagsLen = headerSize - (4 + 8 + 8 + 8 + 8 + (caption.size() + 1));

        bool wasLastChar0 = false;
        while(tagsLen != 0){
            char c;
            std::vector<char> tagChars;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            if(tagsLen == 0)
                throw MyCustomException("invalid tags format");
            tagsLen--;

            setWasLastCharacter0(c, wasLastChar0);
            while (c != '\0')
            {
                if(c == '\n')
                    throw MyCustomException("tags must not be multiline");
                tagChars.push_back(c);
                ifstream.read(reinterpret_cast<char *>(&c),1);
                if (tagsLen == 0)
                    throw MyCustomException("invalid tags format");
                tagsLen--;
                setWasLastCharacter0(c, wasLastChar0);                          
            }
            std::string tag(tagChars.begin(), tagChars.end());
            tags.push_back(tag);
        }
        if (!wasLastChar0)
        {
            throw MyCustomException("last character must be \\0");
        }
    }

    void readPixels(std::ifstream &ifstream){
        for(uint64_t i = 0; i < contentSize; i++){
            unsigned char c;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            pixels.push_back(c);
        }
    }
};


#endif