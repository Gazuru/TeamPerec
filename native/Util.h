#ifndef UTIL_H
#define UTIL_H



struct Util
{
    static bool isAdditionOverflow(uint64_t left, uint64_t right) {
        //if (left + right < left || left + right < right) return true;
        //else return false;

        if (UINT64_MAX - left < right) return true;
        else return false;
    }

    static bool isMultiplicationOverflow(uint64_t left, uint64_t right) {
        if (right != 0 && left > UINT64_MAX / right) { // if you multiply by right, you get: left * right > UINT64_MAX
            return true;
        }
        else return false;
    }
};


#endif