#ifndef CAFFHEADER_H
#define CAFFHEADER_H

#include <fstream>
#include "MyCustomException.h"


using namespace std;

class CaffHeader
{
    public:
    uint64_t headerSize{};
    uint64_t numAnim{};
    CaffHeader(){

    }
    ~CaffHeader(){

    }

    void init(std::ifstream &ifstream){
        unsigned int id{};
        ifstream.read(reinterpret_cast<char *>(&id), 1);
        if(id != 1){
            throw MyCustomException("header id must be 1");
            
        }

        uint64_t length;
        ifstream.read(reinterpret_cast<char *>(&length), 8);

        char magic[4];
        ifstream.read(reinterpret_cast<char *>(&magic), 4);
        if(!(magic[0] == 'C' && magic[1] == 'A' && magic[2] == 'F' && magic[3] == 'F')){
            throw MyCustomException("magic is not present");
        }
        ifstream.read(reinterpret_cast<char *>(&headerSize), 8);
        //header size must be 20
        if(headerSize!= 20)
            throw MyCustomException("header size is not correct");

        ifstream.read(reinterpret_cast<char *>(&numAnim), 8);

        //length must be 20 (4+8+8)
        if(length != 20)
            throw MyCustomException("length size is not correct");
    }
};


#endif