#ifndef CAFFCREDITS_H
#define CAFFCREDITS_H
#include <string>
#include <fstream>
#include <iostream>
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
        /*unsigned int id{};
        ifstream.read(reinterpret_cast<char *>(&id), 1);
        if(id != 2){
            throw invalid_argument("credit id must be 2");
        }*/

        //TODO - validate with length
        uint64_t length;
        ifstream.read(reinterpret_cast<char *>(&length), 8);

        ifstream.read(reinterpret_cast<char *>(&year), 2);
        ifstream.read(reinterpret_cast<char *>(&month), 1);
        ifstream.read(reinterpret_cast<char *>(&day), 1);
        ifstream.read(reinterpret_cast<char *>(&hour), 1);
        ifstream.read(reinterpret_cast<char *>(&minute), 1);
        ifstream.read(reinterpret_cast<char *>(&creatorLength), 8);

        std::vector<char> creatorChars;
        for(int i = 0; i < creatorLength; i++){
            char c;
            ifstream.read(reinterpret_cast<char *>(&c),1);
            creatorChars.push_back(c);       
        }
        std::string s(creatorChars.begin(), creatorChars.end());
        creator = s;
        
        //ifstream.read(reinterpret_cast<char *>(&creatorChars.front()), creatorLength);
        std::cout << "creator is: " << creator << endl;

        if(length != 6+8+creatorLength)
            throw invalid_argument("length size is not correct");
    }
};


#endif