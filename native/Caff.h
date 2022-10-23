#include "Ciff.h"
#include "CaffAnimation.h"
#include "CaffCredits.h"
#include "CaffHeader.h"

struct Caff
{
    CaffHeader caffHeader;
    CaffCredits caffCredits;
    std::vector<CaffAnimation> caffAnimations;
};
