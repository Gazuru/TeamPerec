#include "Caff.h"
#include "Ciff.h"
#include "CaffHeader.h"
#include "CaffAnimation.h"
#include "CaffCredits.h"
#include <iostream>
#include <fstream>
using namespace std;

int main(int, char **)
{
    ifstream ifstream("3.caff", ios::binary);

    Caff caff{};
    
    caff.caffHeader.init(ifstream);

    bool wasCredit = false;
    unsigned int id{};
    while (caff.caffHeader.numAnim > caff.caffAnimations.size())
    {
        ifstream.read(reinterpret_cast<char *>(&id), 1);
        CaffAnimation CaffAnimation;
        switch (id)
        {
        case 2:
            if (wasCredit)
            {
                throw invalid_argument("A CAFF_CREDIT block has already been received");
            }
            caff.caffCredits.init(ifstream);
            wasCredit = true;
            break;
        case 3:
            CaffAnimation.init(ifstream);
            caff.caffAnimations.push_back(CaffAnimation);
            break;
        default:
            throw invalid_argument("The id given is invalid");
            break;
        }
    }
    if (!wasCredit)
    {
        throw invalid_argument("The id given is invalid");
    }
    

    ifstream.close();
    return 0;
}
