#ifndef CAFFANIMATION_H
#define CAFFANIMATION_H

#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include "Ciff.h"
using namespace std;

struct CaffAnimation
{
    CaffAnimation(){
        
    }
    ~CaffAnimation(){
        
    }
    uint64_t duration{};
    Ciff image;

    void init(std::ifstream &ifstream){
        /*unsigned int id{};
        ifstream.read(reinterpret_cast<char *>(&id), 1);
        if(id != 3){
            throw invalid_argument("header id must be 3");
        }*/

        //TODO - validate with length
        uint64_t length;
        ifstream.read(reinterpret_cast<char *>(&length), 8);
        
        ifstream.read(reinterpret_cast<char *>(&duration), 8);
        image.init(ifstream);
    }
};


#endif