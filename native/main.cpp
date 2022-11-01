#include "Caff.h"
#include "Ciff.h"
#include "CaffHeader.h"
#include "CaffAnimation.h"
#include "CaffCredits.h"
#include <iostream>
#include <fstream>
#include "MyCustomException.h"
#include "gif.h"
using namespace std;



void run(std::ifstream& ifstream, string outputFileName) {


    

    Caff caff{};

    caff.caffHeader.init(ifstream);

    bool wasCredit = false;
    unsigned int id{};
    while (caff.caffHeader.numAnim > caff.caffAnimations.size())
    {
        ifstream.read(reinterpret_cast<char*>(&id), 1);
        CaffAnimation CaffAnimation;
        switch (id)
        {
        case 2:
            if (wasCredit)
            {
                throw MyCustomException("A CAFF_CREDIT block has already been received");
            }
            caff.caffCredits.init(ifstream);
            wasCredit = true;
            break;
        case 3:
            CaffAnimation.init(ifstream);
            caff.caffAnimations.push_back(CaffAnimation);
            break;
        default:
            throw MyCustomException("The id given is invalid");
            break;
        }
    }
    if (!wasCredit)
    {
        throw MyCustomException("The id given is invalid");
    }

    //convert to GIF

    auto fileName = outputFileName + ".gif";
    int delay = 100;
    GifWriter g;
    GifBegin(&g, fileName.c_str(), caff.caffAnimations.at(0).image.width, caff.caffAnimations.at(0).image.height, delay);
    //we must also use uint64_t because num_anim is 8 byte long
    for (uint64_t i = 0; i < caff.caffAnimations.size(); i++) { 
        auto caffAnim = caff.caffAnimations.at(i);
      

        GifWriteFrame(&g, caffAnim.image.pixelsAsRGBA8().data(), caffAnim.image.width, caffAnim.image.height, caffAnim.duration/10);
    }
    GifEnd(&g);
    

}

int main(int argc, char ** argv)
{

    try {
        if (argc != 3)
            throw MyCustomException("invalid number of arguements");
    }
    catch (MyCustomException e) {
        std::cout << "Message: " << e.what() << "\n";
        return 1;
    }
    //string inputFileName = "1.caff";
    //string outputFileName = "mytestgifff";
    string inputFileName = argv[1];
    string outputFileName = argv[2];

    ifstream ifstream(inputFileName, ios::binary);
    

    try {


        if (!ifstream.is_open()) {
            throw MyCustomException("could not open file");
        }

        run(ifstream, outputFileName);
        ifstream.close();
    }
    catch (MyCustomException e) {
        std::cout << "Message: " << e.what() << "\n";
        ifstream.close();
    }
    catch (...) {
        ifstream.close();
    }
    
    

    return 0;
}


