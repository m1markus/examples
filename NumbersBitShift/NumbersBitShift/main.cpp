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

typedef unsigned char byte;

byte emo_buffer24[24];

const char * digit_0[] = {
    " ##     ",
    "#  #    ",
    "#  #    ",
    "#  #    ",
    "#  #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * digit_1[] = {
    "  #     ",
    " ##     ",
    "# #     ",
    "  #     ",
    "  #     ",
    "  #     ",
    "  #     ",
    "        "
};

const char * digit_2[] = {
    " ##     ",
    "#  #    ",
    "   #    ",
    "  #     ",
    " #      ",
    "#       ",
    "####    ",
    "        "
};

const char * digit_3[] = {
    " ##     ",
    "#  #    ",
    "   #    ",
    "  #     ",
    "   #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * digit_4[] = {
    "#  #    ",
    "#  #    ",
    "#  #    ",
    "####    ",
    "   #    ",
    "   #    ",
    "   #    ",
    "        "
};

const char * digit_5[] = {
    "####    ",
    "#       ",
    "#       ",
    "###     ",
    "   #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * digit_6[] = {
    " ##     ",
    "#  #    ",
    "#       ",
    "###     ",
    "#  #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * digit_7[] = {
    "####    ",
    "   #    ",
    "  #     ",
    " #      ",
    "#       ",
    "#       ",
    "#       ",
    "        "
};

const char * digit_8[] = {
    " ##     ",
    "#  #    ",
    "#  #    ",
    " ##     ",
    "#  #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * digit_9[] = {
    " ##     ",
    "#  #    ",
    "#  #    ",
    " ###    ",
    "   #    ",
    "#  #    ",
    " ##     ",
    "        "
};

const char * sep_minutes[] = {
    "        ",
    "        ",
    "#       ",
    "        ",
    "#       ",
    "        ",
    "        ",
    "        "
};

byte b_digit_0[8];
byte b_digit_1[8];
byte b_digit_2[8];
byte b_digit_3[8];
byte b_digit_4[8];
byte b_digit_5[8];
byte b_digit_6[8];
byte b_digit_7[8];
byte b_digit_8[8];
byte b_digit_9[8];
byte b_sep_minutes[8];

int binstr2ul(const char *s, unsigned long *num);
void strreplace(char s[], char chr, char repl_chr);


void convert_string_digit_to_byte_digit(const char *p[], byte *pOut)
{
    char sz_buff[16];
    for(int ii = 0; ii < 8; ii++) {
        unsigned long ulByte;
        strcpy(sz_buff, p[ii]);
        strreplace(sz_buff, ' ', '0');
        strreplace(sz_buff, '#', '1');
        binstr2ul(sz_buff, &ulByte);
        byte outChar = (byte)ulByte;
        *pOut++ = outChar;
    }
}

void init_b_digit()
{
    convert_string_digit_to_byte_digit(digit_0, b_digit_0);
    convert_string_digit_to_byte_digit(digit_1, b_digit_1);
    convert_string_digit_to_byte_digit(digit_2, b_digit_2);
    convert_string_digit_to_byte_digit(digit_3, b_digit_3);
    convert_string_digit_to_byte_digit(digit_4, b_digit_4);
    convert_string_digit_to_byte_digit(digit_5, b_digit_5);
    convert_string_digit_to_byte_digit(digit_6, b_digit_6);
    convert_string_digit_to_byte_digit(digit_7, b_digit_7);
    convert_string_digit_to_byte_digit(digit_8, b_digit_8);
    convert_string_digit_to_byte_digit(digit_9, b_digit_9);
    
    convert_string_digit_to_byte_digit(sep_minutes, b_sep_minutes);
}

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
            return -1;
        } else if ('1' == *s) {
            rc = (rc * 2) + 1;
        } else if ('0' == *s) {
            rc *= 2;
        } else {
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
        return NULL;
    } else {
        s[--len] = '\0';
        std::memset(s, '0', len);
    }
    
    do {
        if (0 == len) {
            return NULL;
        } else {
            s[--len] = ((num & 1) ? '1' : '0');
        }
    } while ((num >>= 1) != 0);
    
    return s + len;
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

void convert_3_digit_to_emo_buffer(const char *digit_1[], const char *digit_2[], const char *digit_3[], unsigned char *emo_buffer24, int emo_buffer_size)
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

byte *get_digit_byte_array_from_time_char(char inChar)
{
    byte *pRet;
    
    switch(inChar) {
        case '0':
            pRet = b_digit_0;
            break;
        case '1':
            pRet = b_digit_1;
            break;
        case '2':
            pRet = b_digit_2;
            break;
        case '3':
            pRet = b_digit_3;
            break;
        case '4':
            pRet = b_digit_4;
            break;
        case '5':
            pRet = b_digit_5;
            break;
        case '6':
            pRet = b_digit_6;
            break;
        case '7':
            pRet = b_digit_7;
            break;
        case '8':
            pRet = b_digit_8;
            break;
        case '9':
            pRet = b_digit_9;
            break;
        case ':':
            pRet = b_sep_minutes;
            break;
        default:
            pRet = nullptr;
    }
    return pRet;
}

void convert_time_string_to_emo_buffer(char *pszTimeString, unsigned char *emo_buffer24, int emo_buffer_size)
{
    byte *hour_1 = get_digit_byte_array_from_time_char(pszTimeString[0]);
    byte *hour_2 = get_digit_byte_array_from_time_char(pszTimeString[1]);
    byte *sep = get_digit_byte_array_from_time_char(pszTimeString[2]);
    byte *min_1 = get_digit_byte_array_from_time_char(pszTimeString[3]);
    byte *min_2 = get_digit_byte_array_from_time_char(pszTimeString[4]);
    
    //  l2 = (1 << 7)|(1 << 3);
    int emo_index = 0;
    
    for (int ii = 0; ii < 8; ii++) {
        
        // screen 1
        //
        emo_buffer24[emo_index] = (hour_1[ii] >> 1) | (hour_2[ii] >> 6);
        emo_index++;
        
        // screen 2
        //
        emo_buffer24[emo_index] = (hour_2[ii] << 2) | (sep[ii] >> 3) | (min_1[ii] >> 5);
        emo_index++;
        
        // screen 3
        //
        emo_buffer24[emo_index] = (min_1[ii] << 3) | (min_2[ii] >> 2);
        emo_index++;
    }
}

// end copy to arduino

// begin only on macbook

void print_digit(const char *digit_8[]) {
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
void print_emo_buffer(unsigned char *pCharArray24, int charArray24Size, int isPrettyPrint)
{
    char sz_digit_1[9];
    char sz_digit_2[9];
    char sz_digit_3[9];
    int charArray24Index = 0;
    
    for (int ii=0; ii < 8; ii++) {
        
        ul2binstr(pCharArray24[charArray24Index++], sz_digit_1, sizeof sz_digit_1);
        ul2binstr(pCharArray24[charArray24Index++], sz_digit_2, sizeof sz_digit_2);
        ul2binstr(pCharArray24[charArray24Index++], sz_digit_3, sizeof sz_digit_3);
        
        if (isPrettyPrint) {
            strreplace(sz_digit_1, '0', ' ');
            strreplace(sz_digit_1, '1', '#');
            
            strreplace(sz_digit_2, '0', ' ');
            strreplace(sz_digit_2, '1', '#');
            
            strreplace(sz_digit_3, '0', ' ');
            strreplace(sz_digit_3, '1', '#');
        }
        
        std::printf("%s %s %s\n", sz_digit_1, sz_digit_2, sz_digit_3);
    }
}

void print_byte_array_as_bit_string(byte *bNumber, int nSize)
{
    char szBits[8+1];
    
    for (int ii=0; ii<nSize; ii++) {
        unsigned char oneChar = bNumber[ii];
        //std::printf("char: %c\n", oneChar);
        //convert_byte_to_string(oneChar, szBits, sizeof(szBits));
        ul2binstr((unsigned long)oneChar, szBits, sizeof(szBits));
        //convert_byte_to_bit_string(oneChar, szBits, sizeof(szBits));
        std::printf("bit string: %s\n", szBits);
    }
}

int main(int argc, const char * argv[]) {
    std::cout << "NumberBitShift running...\n";
    char sz_time[6];
    
    init_b_digit();
    
    char bNumber[] = {
        0x05,
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H'
    };
    
    print_byte_array_as_bit_string(b_digit_0, 8);
    
    printf("\n");
    
    print_digit(digit_8);
    
    convert_3_digit_to_emo_buffer(digit_1, digit_3, digit_8, emo_buffer24, 24);
    
    // now the buffer is ready for the EMO 24x3 screen device
    
    print_emo_buffer(emo_buffer24, 24, 0);
    print_emo_buffer(emo_buffer24, 24, 1);
    
    snprintf(sz_time, sizeof(sz_time), "23:59");
    
    convert_time_string_to_emo_buffer(sz_time, emo_buffer24, 24);
    print_emo_buffer(emo_buffer24, 24, 0);
    print_emo_buffer(emo_buffer24, 24, 1);

    //
    //
    char sz_buf_t1[9];
    unsigned long l1, l2;
    
    printf("  new operations...\n");
    
    l1 = 0;
    
    /*
    std::strcpy(sz_buf_t1, "10000000");
    binstr2ul(sz_buf_t1, &l1);
    printf("bits %s  dec %lu hex=%lx\n", sz_buf_t1, l1, l1);
    */
    
    l2 = l1;
    l2 = (1 << 7)|(1 << 3);

    std::strcpy(sz_buf_t1, "deadbeef");
    ul2binstr(l2, sz_buf_t1, 9);
    printf("bits %s  dec %lu hex=%lx\n", sz_buf_t1, l2, l2);

    std::cout << "### end ###\n";
    return 0;
}
