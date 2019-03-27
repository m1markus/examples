//
//  main.cpp
//  NumbersBitShift
//
//  Created by Markus Müller on 27.03.19.
//  Copyright © 2019 Markus Müller. All rights reserved.
//

#include <iostream>

// 24 x 8 .... 24 bytes a 8 bit
//

//using namespace std;


unsigned char bNumber[] = {
    0x05,
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H'
};

// from: https://www.dreamincode.net/forums/topic/195111-how-to-convert-binary-to-decimal-using-c-programming/
//
// char c = convert_bit_string_to_byte("1001");
//
unsigned char convert_bit_string_to_byte(char *binstring)
{
    unsigned char decimalval = 0;
    int stringpos;

    for (stringpos=(int)strlen(binstring)-1; stringpos>=0; stringpos--) {
        decimalval = decimalval<<1;
        if (binstring[stringpos]=='1') decimalval += 1;
    }

    return decimalval;
}

// from: https://www.techworld.com.au/article/527677/how_convert_an_ascii_char_binary_string_representation_c/
//
// unsigned char szBits[8+1];
// convert_byte_to_bit_string(oneChar, szBits, sizeof(szBits));
//
void convert_byte_to_bit_string(unsigned char input, unsigned char *output, int outStringSize)
{
    int remainder;
	int BASE=2;
	int DIGITS = (sizeof input) * 8;
	
    char digitsArray[3] = "01";
    //char digitsArray[3] = "_#";
	
	//printf("sizeof char: %d\n", DIGITS);	  
    for (int ii=DIGITS; ii > 0; --ii) {
        remainder = input % BASE;
        input = input / BASE;
        output[ii - 1] = digitsArray[remainder];
    }
    // null terminate output
    output[outStringSize -1] = '\0';
}

void print_byte_array_as_bit_string(unsigned char *bNumber, int nSize)
{
    unsigned char szBits[8+1];
    
    for (int ii=0; ii<nSize; ii++) {
        unsigned char oneChar = bNumber[ii];
        //std::printf("char: %c\n", oneChar);
        //convert_byte_to_string(oneChar, szBits, sizeof(szBits));
        convert_byte_to_bit_string(oneChar, szBits, sizeof(szBits));
        std::printf("bit string: %s\n", szBits);
    }
}

void print_digit(char digit_8[8][9]) {
    for (int ii=0; ii<8; ii++) {
        printf("digit: %s\n", digit_8[ii]);
    }
}

int main(int argc, const char * argv[]) {
    std::cout << "NumberBitShift running...\n";
    
    char digit_8[8][9] = {
        "  ####  ",
        "  #  #  ",
        "  #  #  ",
        "  ####  ",
        "  #  #  ",
        "  #  #  ",
        "  ####  ",
        "        "
    };
    
    print_byte_array_as_bit_string(bNumber, 8);
    
    printf("\n");
    
    print_digit(digit_8);
    
    std::cout << "### end ###\n";
    return 0;
}
