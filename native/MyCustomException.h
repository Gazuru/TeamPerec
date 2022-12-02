#ifndef MYCUSTOMEXCEPTION_H
#define MYCUSTOMEXCEPTION_H
using namespace std;

class MyCustomException : public std::exception {
private:
    char* message;

public:
    MyCustomException(char* msg) : message(msg) {}
    char* what() {
        return message;
    }
};

#endif
