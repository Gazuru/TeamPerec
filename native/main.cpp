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



void run() {
    string inputFileName = "3.caff";

    ifstream ifstream(inputFileName, ios::binary);

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


    ifstream.close();


    //convert to GIF

    auto fileName = "test.gif";
    int delay = 100;
    GifWriter g;
    GifBegin(&g, fileName, caff.caffAnimations.at(0).image.width, caff.caffAnimations.at(0).image.height, delay);
    for (int i = 0; i < caff.caffAnimations.size(); i++) { 
        auto caffAnim = caff.caffAnimations.at(i);
      

        GifWriteFrame(&g, caffAnim.image.pixelsAsRGBA8().data(), caffAnim.image.width, caffAnim.image.height, caffAnim.duration/10);
    }
    GifEnd(&g);
    

}

int main(int, char **)
{

    try {
        run();
    }
    catch (MyCustomException e) {
        std::cout << "Message: " << e.what() << "\n";
    }
    

    return 0;
}


