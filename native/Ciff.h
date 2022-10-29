
#ifndef CIFF_H
#define CIFF_H

#include <string>
#include <vector>
#include <fstream>
#include "MyCustomException.h"
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
    void init(std::ifstream &ifstream){
        char magic[4];
        ifstream.read(reinterpret_cast<char *>(&magic), 4);
        if(!(magic[0] == 'C' && magic[1] == 'I' && magic[2] == 'F' && magic[3] == 'F')){
            //TODO Lecserélni normális kivételre
            throw MyCustomException("magic is not present");
        }
        ifstream.read(reinterpret_cast<char *>(&headerSize), 8);
        ifstream.read(reinterpret_cast<char *>(&contentSize), 8);
        ifstream.read(reinterpret_cast<char *>(&width), 8);
        ifstream.read(reinterpret_cast<char *>(&height), 8);

        if((width == 0 || height == 0) && contentSize != 0){
            throw MyCustomException("no pixels should be present according to size given");
        }
        
        if (contentSize != width*height*3)
        {
            throw MyCustomException("content size not width*height*3");
        }
        //todo - read function where we increamenta a value for each byte read, after it exceeds headerSize error will be thrown

        readCaption(ifstream);

        readTags(ifstream);

        readPixels(ifstream);
    }
    std::vector<unsigned char> pixelsAsRGBA8() {
        vector<unsigned char> rgba8Pixels {};

        for (int i = 0; i < pixels.size(); i++) {   
            if (i % 3 == 0 && i != 0) {
                rgba8Pixels.push_back(255);
            }
            rgba8Pixels.push_back(pixels.at(i));
        }
        rgba8Pixels.push_back(255);
        return rgba8Pixels;
    }

    void readCaption(std::ifstream &ifstream){
        char c;
        std::vector<char> captionChars;
        ifstream.read(reinterpret_cast<char *>(&c),1);
        while (c != '\n')
        {
            captionChars.push_back(c);
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
        uint64_t tagsLen = headerSize - (4+8+8+8+8+(caption.size()+1));
        bool wasLastChar0 = false;
        while(tagsLen != 0){
            char c;
            std::vector<char> tagChars;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            tagsLen--;
            setWasLastCharacter0(c, wasLastChar0);
            while (c != '\0')
            {
                tagChars.push_back(c);
                ifstream.read(reinterpret_cast<char *>(&c),1);
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
        for(int i = 0; i < contentSize; i++){
            unsigned char c;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            pixels.push_back(c);
        }
    }
};


#endif