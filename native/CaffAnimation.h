#ifndef CAFFANIMATION_H
#define CAFFANIMATION_H

#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include "Ciff.h"
#include "MyCustomException.h"
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
        uint64_t length;
        ifstream.read(reinterpret_cast<char *>(&length), 8);
        
        ifstream.read(reinterpret_cast<char *>(&duration), 8);  
        
        image.init(ifstream, length-8);

        //length = 8 + ciff whole size is already checked in Ciff.init()
        

    }
};


#endif