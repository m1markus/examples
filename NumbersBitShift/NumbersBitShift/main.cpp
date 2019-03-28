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

// from: https://codereview.stackexchange.com/questions/43256/binary-string-to-integer-and-integer-to-binary-string
//
/*
 Parameters:
 s   - String with a maximum of log2(ULONG_MAX) binary characters
 num - Memory address to store the result in
 Return:
 - Status integer
 Error:
 - Negative returned on error check errno
 */
int binstr2ul(const char *s, unsigned long *num)
{
    unsigned long rc;
    for (rc = 0; '\0' != *s; s++) {
        if (rc > (ULONG_MAX/2)) {
            errno = ERANGE;
            return -1;
        } else if ('1' == *s) {
            rc = (rc * 2) + 1;
        } else if ('0' == *s) {
            rc *= 2;
        } else {
            errno = EINVAL;
            return -1;
        }
    }
    *num = rc;
    return 0;
}

/*
 Parameters:
 num     - The number to convert to a binary string
 s       - Pointer to a memory region to return the string to
 len     - Size in bytes of the region pointed to by s
 Return:
 - Pointer to the beginning of the string
 Error:
 - NULL returned on error check errno
 */
char *ul2binstr(unsigned long num, char *s, size_t len)
{
    if (0 == len) {
        errno = EINVAL;
        return NULL;
    } else {
        s[--len] = '\0';
    }
    
    do {
        if (0 == len) {
            errno = ERANGE;
            return NULL;
        } else {
            s[--len] = ((num & 1) ? '1' : '0');
        }
    } while ((num >>= 1) != 0);
    
    return s + len;
}

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

void strreplace(char s[], char chr, char repl_chr)
{
    int i=0;
    while(s[i]!='\0')
    {
        if(s[i]==chr)
        {
            s[i]=repl_chr;
        }
        i++;
    }
    return;
}

void convert_3_digit_to_emo_buffer(char digit_1[8][9], char digit_2[8][9], char digit_3[8][9], unsigned char *emo_buffer24, int emo_buffer_size)
{
    unsigned long ul_char;
    int emo_buffer_index = 0;
    int rc;
    char sz_buff[16];
    
    for (int ii=0; ii<8; ii++) {
        
        // digit 1
        //
        strcpy(sz_buff, digit_1[ii]);
        strreplace(sz_buff, ' ', '0');
        strreplace(sz_buff, '#', '1');
        rc = binstr2ul(sz_buff, &ul_char);
        emo_buffer24[emo_buffer_index++] = (unsigned char)ul_char;
        
        // digit 2
        //
        strcpy(sz_buff, digit_2[ii]);
        strreplace(sz_buff, ' ', '0');
        strreplace(sz_buff, '#', '1');
        rc = binstr2ul(sz_buff, &ul_char);
        emo_buffer24[emo_buffer_index++] = (unsigned char)ul_char;
        
        // digit 3
        //
        strcpy(sz_buff, digit_3[ii]);
        strreplace(sz_buff, ' ', '0');
        strreplace(sz_buff, '#', '1');
        rc = binstr2ul(sz_buff, &ul_char);
        emo_buffer24[emo_buffer_index++] = (unsigned char)ul_char;
        
        // check emp_buffer_index with emo_buffer_size
    }
}

void print_digit(char digit_8[8][9]) {
    for (int ii=0; ii<8; ii++) {
        printf("digit: %s\n", digit_8[ii]);
    }
}

// print the array in the following way...
//
// charArray24[1] charArray24[2] charArray24[3]
// charArray24[4] ...
// ...                           charArray24[23]
//
void print_emo_buffer(unsigned char *pCharArray24, int charArray24Size)
{
    unsigned char sz_digit_1[9];
    unsigned char sz_digit_2[9];
    unsigned char sz_digit_3[9];
    int charArray24Index = 0;
    
    for (int ii=0; ii < 8; ii++) {
        
        convert_byte_to_bit_string(pCharArray24[charArray24Index++], sz_digit_1, sizeof sz_digit_1);
        convert_byte_to_bit_string(pCharArray24[charArray24Index++], sz_digit_2, sizeof sz_digit_2);
        convert_byte_to_bit_string(pCharArray24[charArray24Index++], sz_digit_3, sizeof sz_digit_3);
        
        std::printf("%s %s %s\n", sz_digit_1, sz_digit_2, sz_digit_3);
    }
}

int main(int argc, const char * argv[]) {
    std::cout << "NumberBitShift running...\n";
    
    unsigned char emo_buffer24[24];
    
    char digit_1[8][9] = {
        "   #    ",
        "  ##    ",
        " # #    ",
        "   #    ",
        "   #    ",
        "   #    ",
        "   #    ",
        "        "
    };
    
    char digit_3[8][9] = {
        " ####   ",
        "    #   ",
        "    #   ",
        " ####   ",
        "    #   ",
        "    #   ",
        " ####   ",
        "        "
    };
    
    char digit_8[8][9] = {
        " ####   ",
        " #  #   ",
        " #  #   ",
        " ####   ",
        " #  #   ",
        " #  #   ",
        " ####   ",
        "        "
    };
    
    print_byte_array_as_bit_string(bNumber, 8);
    
    printf("\n");
    
    print_digit(digit_8);
    
    convert_3_digit_to_emo_buffer(digit_1, digit_3, digit_8, emo_buffer24, 24);
    
    // now the buffer is ready for the EMO 24x3 screen device
    
    print_emo_buffer(emo_buffer24, 24);
    
    std::cout << "### end ###\n";
    return 0;
}
