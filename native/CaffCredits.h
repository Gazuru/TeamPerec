#ifndef CAFFCREDITS_H
#define CAFFCREDITS_H
#include <string>
#include <fstream>
#include <iostream>
#include "MyCustomException.h"
#include "Util.h"
using namespace std;

struct CaffCredits
{
    uint64_t year{};
    uint64_t month{};
    uint64_t day{};
    uint64_t hour{};
    uint64_t minute{};
    uint64_t creatorLength{};
    std::string creator{};
    CaffCredits(){

    }
    ~CaffCredits(){

    }
    void init(std::ifstream &ifstream){

        //we will not be validating the dates because it is not in the scope of gif parsing, we do not use these values, we only read them
        uint64_t length;
        ifstream.read(reinterpret_cast<char *>(&length), 8);
        ifstream.read(reinterpret_cast<char *>(&year), 2);
        ifstream.read(reinterpret_cast<char *>(&month), 1);
        ifstream.read(reinterpret_cast<char *>(&day), 1);
        ifstream.read(reinterpret_cast<char *>(&hour), 1);
        ifstream.read(reinterpret_cast<char *>(&minute), 1);
        ifstream.read(reinterpret_cast<char *>(&creatorLength), 8);

        //watch for underflow
        if (length < 14) {
            throw MyCustomException("length does not reach minimum size");
        }
        if (creatorLength > length - 2 - 1 - 1 - 1 - 1 - 8)
            throw MyCustomException("creatorLength exceeds given length limit");

        std::vector<char> creatorChars;
        //we must also iterate with uint64_t to avoid overflow
        for(uint64_t i = 0; i < creatorLength; i++){
            char c;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            creatorChars.push_back(c);
        }
        std::string s(creatorChars.begin(), creatorChars.end());
        creator = s;
      
        //watch for overflow
        if (Util::isAdditionOverflow(6 + 8, creatorLength)) {
            throw MyCustomException("length size is not correct");
        }
        if(length != 6+8+creatorLength)
            throw MyCustomException("length size is not correct");
    }
};


#endif