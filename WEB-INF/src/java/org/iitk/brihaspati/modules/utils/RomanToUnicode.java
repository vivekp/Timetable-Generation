package org.iitk.brihaspati.modules.utils;
//import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
public class RomanToUnicode
{
	private String strUnicode;
	private int  i;
	char currentChar=' ';
	int kc = 0;
	int tempLkey = 0;
	int lastKey = 48;
	int lastKey0 = 48;
	int lastKey1 = 48;
	String lastKeyType = "s";
	String lastKeyType0 = "s";
	String lastKeyType1 = "s";
	String msg = new String();
	String message = new String();
	String [][] uni = new String[123][4];
	String [][] combo = new String[35][7];
	String [] vov = new String[6];
	String seed = null;
public String getstrUnicode(String strUnicode, String language)
{
	for(i=0;i<strUnicode.length();i++)
	{
		if(i<strUnicode.length()) currentChar=strUnicode.charAt(i);
		try{
			kc=(int)currentChar;
			//hindiMarathiCharMap();
			
			if (language.equals("hindi") || language.equals("marathi") )
			 	hindiMarathiCharMap();
			if(language.equals("bangla"))
				banglaCharMap();
			
			getHexachar(kc);
		}catch(Exception e){}
	}//for loop
	//ErrorDumpUtil.ErrorLog("message.in util main method........."+message);
	return message;
}//getstrUnicode
/**/
//////////////////////////////////////
public void hindiMarathiCharMap()
{
	seed = "2406";
	//assign numbers
	for(int i=48; i <=57; i++)
	{
		//uni[i] = new [2][2];	
		uni[i][0] = "n";
		uni[i][1] = seed;
		int seedInt = Integer.parseInt(seed);
		seedInt = seedInt + 1;	
		seed = Integer.toString(seedInt);
	}

uni[32][0] = "s";
uni[32][1] = "&#0032;"; 

//uni[94]    = new Array(2); // ^
uni[94][0] = "m";
uni[94][1] = "&#2306;"; 
uni[94][2] = "m";
uni[94][3] = "&#2306;"; 

//uni[65]    = new Array(4); //A
uni[65][0] = "v";
uni[65][1] = "&#2317;"; 
uni[65][2] = "m";
uni[65][3] = "&#2373;"; 

//uni[66]    = new Array(2); //B
uni[66][0] = "c";
uni[66][1] = "&#2348;";

//uni[67]    = new Array(2); //C
uni[67][0] = "s";
uni[67][1] = "&#2416;";

//uni[68]  =	new Array(2); //D
uni[68][0] = "c";
uni[68][1] = "&#2337;" ;

//uni[69]  =	new Array(2); //E
uni[69][0] = "v";
uni[69][1] = "&#2317;";
uni[69][2] = "m";
uni[69][3] = "&#2373;";

//uni[70]  =	new Array(2); //F
uni[70][0] = "c";
uni[70][1] = "&#2398;";

//uni[71]  =	new Array(2); //G
uni[71][0] = "c";
uni[71][1] = "&#2394;";

//uni[72] =	new Array(2); //H
uni[72][0] = "m";
uni[72][1] = "&#2307;";
uni[72][2] = "m";
uni[72][3] = "&#2307;";

//uni[73]  =	new Array(2); //I
uni[73][0] = "v";
uni[73][1] = "&#2311;";

//uni[74]  =	new Array(2); //J
uni[74][0] = "c";
uni[74][1] = "&#2395;";

//uni[75]  =	new Array(2); //K
uni[75][0] = "c";
uni[75][1] = "&#2392;";

//uni[76]  =	new Array(2); //L
uni[76][0] = "c";
uni[76][1] = "&#2355;";

//uni[77]    =   new Array(2); //M
uni[77][0] = "m";
uni[77][1] = "&#2305;" ;
uni[77][2] = "m";
uni[77][3] = "&#2305;" ;

//uni[78]  =	new Array(2); //N
uni[78][0] = "c";
uni[78][1] = "&#2339;";

//uni[79]  =	new Array(4); //O
uni[79][0] = "v";
uni[79][1] = "&#2321;";
uni[79][2] = "m";
uni[79][3] = "&#2377;";

//uni[80]  =	new Array(2); //P
uni[80][0] = "c";
uni[80][1] = "&#2346;";

//uni[81]  =	new Array(2); //Q
uni[81][0] = "c";
uni[81][1] = "&#2392;";

//uni[82]  =	new Array(2); //R
uni[82][0] = "v";
uni[82][1] = "&#2315;";
uni[82][2] = "m";
uni[82][3] = "&#2371;";

//uni[83]  =	new Array(2); //S
uni[83][0] = "c";
uni[83][1] = "&#2360;";

//uni[84]  =	new Array(2); //T
uni[84][0] = "c";
uni[84][1] = "&#2335;";

//uni[85]  =	new Array(2); //U
uni[85][0] = "v";
uni[85][1] = "&#2313;";

//uni[86]  =	new Array(2); //V
uni[86][0] = "c";
uni[86][1] = "&#2357;";

//uni[87]  =	new Array(2); //W
uni[87][0] = "c";
uni[87][1] = "&#2357;";

//uni[88]  =	new Array(2); //X
uni[88][0] = "c";
uni[88][1] = "";

//uni[89]  =	new Array(2); //Y
uni[89][0] = "c";
uni[89][1] = "&#2399;";

//uni[90]  =	new Array(2); //Z
uni[90][0] = "c";
uni[90][1] = "&#2364;";

//uni[97]  =	new Array(4); //a
uni[97][0] = "m";
uni[97][1] = "";
uni[97][2] = "v";
uni[97][3] = "&#2309;";

//uni[98]  =	new Array(2); //b
uni[98][0] = "c";
uni[98][1] = "&#2348;";

//uni[99]  =	new Array(2); //c
uni[99][0] = "c";
//uni[99][1] = "&#2325;";
uni[99][1] ="&#2330;"; 

//uni[100] =	new Array(2); //d
uni[100][0] = "c";
uni[100][1] = "&#2342;";

//uni[101] =	new Array(2); //e
uni[101][0] = "m";
uni[101][1] = "&#2375;";
uni[101][2] = "v";
uni[101][3] = "&#2319;";

//uni[102] =	new Array(2); //f
uni[102][0] = "c";
uni[102][1] = "&#2347;";

//uni[103] =	new Array(2); //g
uni[103][0] = "c";
uni[103][1] = "&#2327;";

//uni[104] =	new Array(2); //h
uni[104][0] = "c";
uni[104][1] = "&#2361;";

//uni[105] =	new Array(2); //i
uni[105][0] = "m";
uni[105][1] = "&#2367;";
uni[105][2] = "v";
uni[105][3] = "&#2311;";


//uni[106] =	new Array(2); //j
uni[106][0] = "c"; 
uni[106][1] = "&#2332;";

//uni[107] =	new Array(2); //k
uni[107][0] = "c"; 
uni[107][1] = "&#2325;";

//uni[108] =	new Array(2); //l
uni[108][0] = "c"; 
uni[108][1] = "&#2354;";

//uni[109] =	new Array(2); //m
uni[109][0] = "c"; 
uni[109][1] = "&#2350;";

//uni[110] =	new Array(2); //n
uni[110][0] = "c"; 
uni[110][1] = "&#2344;";

//uni[111] =	new Array(4); //o
uni[111][0] = "m"; 
uni[111][1] = "&#2379;";
uni[111][2] = "v";
uni[111][3] = "&#2323;"; 

//uni[112] = 	new Array(2); //p
uni[112][0] = "c"; 
uni[112][1] = "&#2346;";

//uni[113] =	new Array(2); //q
//uni[113][0] = "c"; 
//uni[113][1] = "&#2392;";
uni[113][0] = "s";
uni[113][1] = "&#2305;";

//uni[114] =	new Array(2); //r
uni[114][0] = "c"; 
uni[114][1] = "&#2352;";

//uni[115] =	new Array(2); //s
uni[115][0] = "c"; 
uni[115][1] = "&#2360;";

//uni[116] =	new Array(2); //t
uni[116][0] = "c"; 
uni[116][1] = "&#2340;";

//uni[117] =	new Array(2); //u
uni[117][0] = "m"; 
uni[117][1] = "&#2369;";
uni[117][2] = "v"; 
uni[117][3] = "&#2313;";

//uni[118] =	new Array(2); //v
uni[118][0] = "c"; 
uni[118][1] = "&#2357;";

//uni[119] =	new Array(2); //w
uni[119][0] = "c"; 
uni[119][1] = "&#2357;";

//uni[120] =	new Array(2); //x
uni[120][0] = "c"; 
//uni[120][1] = "&#2381;";
uni[120][1] = "&#2325;&#2359;";

//uni[121] =	new Array(2); //y
uni[121][0] = "c"; 
uni[121][1] = "&#2351;";

//uni[122] =	new Array(2); //z
//uni[122][0] = "c"; 
//uni[122][1] = "&#2395;";
uni[122][0] = "s";
uni[122][1] = "&#2404;";

/**/
/*------------- Combo Consonents ----------*/
/* */
//var combo = new Array(35);
//combo[0] = new Array(5);
combo[0][0] = "115"; //s + 
combo[0][1] = "104"; //h = 
combo[0][2] = "&#2358;";	//sh
combo[0][3] = "c";	//dataType

//combo[1] = new Array(5);
combo[1][0] = "98"; //b + 
combo[1][1] = "104"; //h = 
combo[1][2] = "&#2349;";	//bh
combo[1][3] = "c";	//dataType
//combo[2] = new Array(4);
combo[2][0] = "99"; //c + 
combo[2][1] = "104"; //h = 
combo[2][2] = "&#2331;";	//chh
combo[2][3] = "c";	//dataType
combo[2][4] = "104"; //h	
//combo[3] = new Array(5);
combo[3][0] = "67"; //C + 
combo[3][1] = "104"; //h = 
combo[3][2] = "&#2331;";	//Ch
combo[3][3] = "c";	//dataType

//combo[4] = new Array(4);
combo[4][0] = "100"; //d + 
combo[4][1] = "104"; //h = 
combo[4][2] = "&#2343;";	//dh
combo[4][3] = "c";	//dataType

//combo[5] = new Array(4);
combo[5][0] = "68"; //D + 
combo[5][1] = "104"; //h = 
combo[5][2] = "&#2338;";	//Dh
combo[5][3] = "c";	//dataType

//combo[6] = new Array(4);
combo[6][0] = "68"; //D + 
combo[6][1] = "72"; //h = 
combo[6][2] = "&#2329;";	//DH
combo[6][3] = "c";	//dataType

//combo[8] = new Array(4);
combo[8][0] = "106"; //j + 
combo[8][1] = "104"; //h = 
combo[8][2] = "&#2333;";	//jh
combo[8][3] = "c";	//dataType

//combo[9] = new Array(4);
combo[9][0] = "83"; //S + 
combo[9][1] = "104"; //h = 
combo[9][2] = "&#2359;";	//Sh
combo[9][3] = "c";	//dataType

//combo[10] = new Array(4);
combo[10][0] = "112"; //p + 
combo[10][1] = "104"; //h = 
combo[10][2] = "&#2347;";	//ph
combo[10][3] = "c";	//dataType

//combo[11] = new Array(4);
combo[11][0] = "103"; //g + 
combo[11][1] = "104"; //h = 
combo[11][2] = "&#2328;";	//gh
combo[11][3] = "c";	//dataType

//combo[25] = new Array(4);
combo[25][0] = "107"; //k + 
combo[25][1] = "104"; //h = 
combo[25][2] = "&#2326;";	//kh
combo[25][3] = "c";	//dataType

//combo[26] = new Array(4);
combo[26][0] = "126"; //j + 
combo[26][1] = "106"; //h = 
combo[26][2] = "&#2334;";	//jh
combo[26][3] = "c";	//dataType

//combo[27] = new Array(4);
combo[27][0] = "116"; //t + 
combo[27][1] = "104"; //h = 
combo[27][2] = "&#2341;";	//th
combo[27][3] = "c";	//dataType

//combo[28] = new Array(4);
combo[28][0] = "84"; //T + 
combo[28][1] = "104"; //h = 
combo[28][2] = "&#2336;";	//Th
combo[28][3] = "c";	//dataType


//combo[29] = new Array(4);
combo[29][0] = "84"; //R + 
combo[29][1] = "104"; //a = 
combo[29][2] = "&#2336;";	//Th ------- repeat ---- Ra Ru Ri fix this 
combo[29][3] = "c";	//dataType

//combo[31] = new Array(4);
combo[31][0] = "75"; //K + 
combo[31][1] = "104"; //h = 
combo[31][2] = "&#2326;";	//Kh
combo[31][3] = "c";	//dataType

//combo[32] = new Array(4);
combo[32][0] = "78"; //N + 
combo[32][1] = "106"; //j = 
combo[32][2] = "&#2334;";	//Nj
combo[32][3] = "c";	//dataType


//combo[33] = new Array(4);
combo[33][0] = "78"; //N + 
combo[33][1] = "103"; //g = 
combo[33][2] = "&#2329;";	//Ng
combo[33][3] = "c";	//dataType
/* */
/*------------- baDi MaaTraae^ ----------*/
/* */
//combo[12] = new Array(6);
combo[12][0] = "101"; 	//e + 
combo[12][1] = "101"; 	//e = 
combo[12][2] = "&#2368;";	// ii
combo[12][3] = "m";	//dataType
combo[12][4] = "&#2312;";	// ii
combo[12][5] = "v";	//dataType

//combo[13] = new Array(6);
combo[13][0] = "105"; 	//i + 
combo[13][1] = "105"; 	//i = 
combo[13][2] = "&#2368;";	// ii
combo[13][3] = "m";	//dataType
combo[13][4] = "&#2312;";	// ii
combo[13][5] = "v";	//dataType

//combo[14] = new Array(6);
combo[14][0] = "111"; 	//o + 
combo[14][1] = "111"; 	//o = 
combo[14][2] = "&#2380;";	// oo
combo[14][3] = "m";	//dataType
combo[14][4] = "&#2324;";	// O
combo[14][5] = "v";	//dataType
combo[14][6] = "&#2370;";	// oo

//combo[15] = new Array(6);
combo[15][0] = "97"; 	//a + 
combo[15][1] = "105"; 	//i = 
combo[15][2] = "&#2376;";	// ai
combo[15][3] = "m";	//dataType
combo[15][4] = "&#2320;";	// ai
combo[15][5] = "v";	//dataType

//combo[16] = new Array(6);
combo[16][0] = "97"; 	//a + 
combo[16][1] = "117"; 	//u = 
combo[16][2] = "&#2380;";	// au
combo[16][3] = "m";	//dataType
combo[16][4] = "&#2324;";	// aa
combo[16][5] = "v";	//dataType

//combo[17] = new Array(6);
combo[17][0] = "97"; 	//a + 
combo[17][1] = "97"; 	//a = 
combo[17][2] = "&#2366;";	// aa ki matra
combo[17][3] = "m";	//dataType
combo[17][4] = "&#2310;";	// aa Vovel
combo[17][5] = "v";	//dataType

//combo[18] = new Array(6);
combo[18][0] = "65"; 	//A + 
combo[18][1] = "97";	//a = 
combo[18][2] = "&#2310;";	// aa vovel
combo[18][3] = "v";	//dataType
combo[18][4] = "&#2310;";	// aa vovel
combo[18][5] = "v";	//dataType

//combo[19] = new Array(6);
combo[19][0] = "65"; 	//a + 
combo[19][1] = "117";	//u = 
combo[19][2] = "&#2380;";	// au
combo[19][3] = "m";	//dataType
combo[19][4] = "&#2324;";	// aa
combo[19][5] = "v";	//dataType

//combo[20] = new Array(6);
combo[20][0] = "65"; 	//a + 
combo[20][1] = "105";	//i = 
combo[20][2] = "&#2320;";	// ai
combo[20][3] = "v";	//dataType 
//combo[20][3] = "m";	//dataType Change at 20Feb_09
combo[20][4] = "&#2310;";	// aa
combo[20][5] = "v";	//dataType

//combo[21] = new Array(6);
combo[21][0] = "73"; 	//E + 
combo[21][1] = "105";	//I = 
combo[21][2] = "&#2312;";	// aa
combo[21][3] = "m";	//dataType
combo[21][4] = "&#2312;";	// ee
combo[21][5] = "v";	//dataType

//combo[22] = new Array(6);
combo[22][0] = "79"; 	//O(a is wrong) + 
combo[22][1] = "111";	//o = 
combo[22][2] = "&#2312;";	// ee(ao is wrong)
combo[22][3] = "m";	//dataType
combo[22][4] = "&#2314;";	// Oo(au is wrong)
combo[22][5] = "v";	//dataType

//combo[23] = new Array(6);
combo[23][0] = "69"; 	//E + 
combo[23][1] = "101";	//e = 
combo[23][2] = "&#2366;";	// aa Matra
combo[23][3] = "m";	//dataType
combo[23][4] = "&#2312;";	// ee(aa...)
combo[23][5] = "v";	//dataType

//combo[24] = new Array(6);
combo[24][0] = "97"; 	//a + 
combo[24][1] = "97";	//a = 
combo[24][2] = "&#2366;";	// aa Matra
combo[24][3] = "m";	//dataType
combo[24][4] = "&#2310;";	// aa
combo[24][5] = "v";	//dataType


//combo[30] = new Array(6);
combo[30][0] = "79"; 	//O + 
combo[30][1] = "77";	//M = 
combo[30][2] = "&#2305;";	// OM
combo[30][3] = "m";	//dataType
combo[30][4] = "&#2384;";	// aa
combo[30][5] = "v";	//dataType

/////////////////////////////////////

combo[7][0] = "117"; 	      //u + 
combo[7][1] = "117";	      //u = 
combo[7][2] = "&#2370;";     //bade oo ki matra 
combo[7][3] = "m";	      //dataType
combo[7][4] = "&#2314;";     // bada oo
combo[7][5] = "v";	      //dataType
}

public void banglaCharMap()
{
	seed = "&#2534;";
	//assign numbers
	for(int i=48; i <=57; i++)
	{
		//uni[i] = new [2][2];	
		uni[i][0] = "n";
		uni[i][1] = seed;
		seed = seed + 1;	
	}

//uni[32]    = new Array(2); // space
uni[32][0] = "s";
uni[32][1] = "Sp"; 

//uni[94]    = new Array(2); // ^
uni[94][0] = "m";
uni[94][1] = "&#2434;"; 
uni[94][2] = "m";
uni[94][3] = "&#2434;"; 

//uni[65]    = new Array(4); //A
uni[65][0] = "v";
uni[65][1] = "&#2447;"; 
uni[65][2] = "m";
uni[65][3] = "&#2503;"; 

//uni[66]    = new Array(2); //B
uni[66][0] = "c";
uni[66][1] = "&#2476;";

//uni[67]    = new Array(2); //C
uni[67][0] = "c";
uni[67][1] = "&#2553;";

//uni[68]  =	new Array(2); //D
uni[68][0] = "c";
uni[68][1] = "&#2465;" ;

//uni[69]  =	new Array(2); //E
uni[69][0] = "v";
uni[69][1] = "&#2447;";

//uni[70]  =	new Array(2); //F
uni[70][0] = "c";
uni[70][1] = "&#2475;";

//uni[71]  =	new Array(2); //G
uni[71][0] = "c";
uni[71][1] = "&#2455;";

//uni[72] =	new Array(2); //H
uni[72][0] = "m";
uni[72][1] = "&#2435;";
uni[72][2] = "m";
uni[72][3] = "&#2435;";

//uni[73]  =	new Array(2); //I
uni[73][0] = "v";
uni[73][1] = "&#2439;";

//uni[74]  =	new Array(2); //J
uni[74][0] = "c";
uni[74][1] = "&#2460;";

//uni[75]  =	new Array(2); //K
uni[75][0] = "c";
uni[75][1] = "&#2453;";

//uni[76]  =	new Array(2); //L
uni[76][0] = "c";
uni[76][1] = "&#2482;";

//uni[77]    =   new Array(2); //M
uni[77][0] = "m";
uni[77][1] = "&#2433;" ;
uni[77][2] = "m";
uni[77][3] = "&#2433;" ;

//uni[78]  =	new Array(2); //N
uni[78][0] = "c";
uni[78][1] = "&#2467;";

//uni[79]  =	new Array(4); //O
uni[79][0] = "v";
uni[79][1] = "&#2451;";
uni[79][2] = "m";
uni[79][3] = "&#2451;";
//uni[80]  =	new Array(2); //P
uni[80][0] = "c";
uni[80][1] = "&#2474;";

//uni[81]  =	new Array(2); //Q
uni[81][0] = "c";
uni[81][1] = "&#2453;";

//uni[82]  =	new Array(2); //R
uni[82][0] = "v";
uni[82][1] = "&#2443;";
uni[82][2] = "m";
uni[82][3] = "&#2499;";

//uni[83]  =	new Array(2); //S
uni[83][0] = "c";
uni[83][1] = "&#2488;";

//uni[84]  =	new Array(2); //T
uni[84][0] = "c";
uni[84][1] = "&#2463;";

//uni[85]  =	new Array(2); //U
uni[85][0] = "v";
uni[85][1] = "&#2441;";

//uni[86]  =	new Array(2); //V
uni[86][0] = "c";
uni[86][1] = "&#2476;";

//uni[87]  =	new Array(2); //W
uni[87][0] = "c";
uni[87][1] = "&#2476;";

//uni[88]  =	new Array(2); //X
uni[88][0] = "c";
uni[88][1] = "";

//uni[89]  =	new Array(2); //Y
uni[89][0] = "c";
uni[89][1] = "2527";

//uni[90]  =	new Array(2); //Z
uni[90][0] = "c";
uni[90][1] = "&#2492;";

//uni[97]  =	new Array(4); //a
uni[97][0] = "m";
uni[97][1] = "";
uni[97][2] = "v";
uni[97][3] = "&#2437;";

//uni[98]  =	new Array(2); //b
uni[98][0] = "c";
uni[98][1] = "&#2476;";

//uni[99]  =	new Array(2); //c
uni[99][0] = "c";
uni[99][1] = "&#2453;";

//uni[100] =	new Array(2); //d
uni[100][0] = "c";
uni[100][1] = "&#2470;";

//uni[101] =	new Array(2); //e
uni[101][0] = "m";
uni[101][1] = "&#2503;";
uni[101][2] = "v";
uni[101][3] = "&#2447;";

//uni[102] =	new Array(2); //f
uni[102][0] = "c";
uni[102][1] = "&#2475;";

//uni[103] =	new Array(2); //g
uni[103][0] = "c";
uni[103][1] = "&#2455;";

//uni[104] =	new Array(2); //h
uni[104][0] = "c";
uni[104][1] = "&#2489;";

//uni[105] =	new Array(2); //i
uni[105][0] = "m";
uni[105][1] = "&#2495;";
uni[105][2] = "v";
uni[105][3] = "&#2439;";


//uni[106] =	new Array(2); //j
uni[106][0] = "c"; 
uni[106][1] = "&#2460;";

//uni[107] =	new Array(2); //k
uni[107][0] = "c"; 
uni[107][1] = "&#2453;";

//uni[108] =	new Array(2); //l
uni[108][0] = "c"; 
uni[108][1] = "&#2482;";

//uni[109] =	new Array(2); //m
uni[109][0] = "c"; 
uni[109][1] = "&#2478;";

//uni[110] =	new Array(2); //n
uni[110][0] = "c"; 
uni[110][1] = "&#2472;";

//uni[111] =	new Array(4); //o
uni[111][0] = "m"; 
uni[111][1] = "&#2505;";
uni[111][2] = "v";
uni[111][3] = "&#2451;"; 

//uni[112] = 	new Array(2); //p
uni[112][0] = "c"; 
uni[112][1] = "&#2474;";

//uni[113] =	new Array(2); //q
uni[113][0] = "c"; 
uni[113][1] = "&#2453;";

//uni[114] =	new Array(2); //r
uni[114][0] = "c"; 
uni[114][1] = "&#2480;";

//uni[115] =	new Array(2); //s
uni[115][0] = "c"; 
uni[115][1] = "&#2488;";

//uni[116] =	new Array(2); //t
uni[116][0] = "c"; 
uni[116][1] = "&#2468;";

//uni[117] =	new Array(2); //u
uni[117][0] = "m"; 
uni[117][1] = "&#2497;";
uni[117][2] = "v"; 
uni[117][3] = "&#2441;";

//uni[118] =	new Array(2); //v
uni[118][0] = "c"; 
uni[118][1] = "&#2476;";

//uni[119] =	new Array(2); //w
uni[119][0] = "c"; 
uni[119][1] = "&#2476;";

//uni[120] =	new Array(2); //x
uni[120][0] = "c"; 
uni[120][1] = "&#2509;";

//uni[121] =	new Array(2); //y
uni[121][0] = "c"; 
uni[121][1] = "&#2479;";

//uni[122] =	new Array(2); //z
//uni[122][0] = "c"; 
//uni[122][1] = "&#2460;";
uni[122][0] = "s"; 
uni[122][1] = "&#2551;";

/*------------- Combo Consonents ----------*/

//var combo = new Array(35);
//combo[0] = new Array(5);
combo[0][0] = "115"; //s + 
combo[0][1] = "104"; //h = 
combo[0][2] = "&#2486;";	//sh
combo[0][3] = "c";	//dataType

//combo[1] = new Array(5);
combo[1][0] = "98"; //b + 
combo[1][1] = "104"; //h = 
combo[1][2] = "&#2477;";	//bh
combo[1][3] = "c";	//dataType

//combo[2] = new Array(4);
combo[2][0] = "99"; //c + 
combo[2][1] = "104"; //h = 
combo[2][2] = "&#2459;";	//chh
combo[2][3] = "c";	//dataType
combo[2][4] = "104";	//h

//combo[3] = new Array(4);
combo[3][0] = "67"; //C + 
combo[3][1] = "104"; //h = 
combo[3][2] = "&#2459;";	//Ch
combo[3][3] = "c";	//dataType

//combo[4] = new Array(4);
combo[4][0] = "100"; //d + 
combo[4][1] = "104"; //h = 
combo[4][2] = "&#2471;";	//dh
combo[4][3] = "c";	//dataType

//combo[5] = new Array(4);
combo[5][0] = "68"; //D + 
combo[5][1] = "104"; //h = 
combo[5][2] = "&#2466;";	//Dh
combo[5][3] = "c";	//dataType

//combo[6] = new Array(4);
combo[6][0] = "68"; //D + 
combo[6][1] = "72"; //h = 
combo[6][2] = "&#2457;";	//DH
combo[6][3] = "c";	//dataType

//combo[7] = new Array(4);
/**
combo[7][0] = "115"; //s + 
combo[7][1] = "104"; //h = 
combo[7][2] = "&#2486;";	//chh <-- Handle with care 
combo[7][3] = "c";	//dataType
**/
//combo[8] = new Array(4);
combo[8][0] = "106"; //j + 
combo[8][1] = "104"; //h = 
combo[8][2] = "&#2461;";	//jh
combo[8][3] = "c";	//dataType

//combo[9] = new Array(4);
combo[9][0] = "83"; //S + 
combo[9][1] = "104"; //h = 
combo[9][2] = "&#2487;";	//Sh
combo[9][3] = "c";	//dataType

//combo[10] = new Array(4);
combo[10][0] = "112"; //p + 
combo[10][1] = "104"; //h = 
combo[10][2] = "&#2475;";	//ph
combo[10][3] = "c";	//dataType

//combo[10] = new Array(4);
combo[10][0] = "112"; //p + 
combo[10][1] = "104"; //h = 
combo[10][2] = "&#2475;";	//ph
combo[10][3] = "c";	//dataType

//combo[11] = new Array(4);
combo[11][0] = "103"; //g + 
combo[11][1] = "104"; //h = 
combo[11][2] = "&#2456;";	//gh
combo[11][3] = "c";	//dataType

//combo[25] = new Array(4);
combo[25][0] = "107"; //k + 
combo[25][1] = "104"; //h = 
combo[25][2] = "&#2454;";	//kh
combo[25][3] = "c";	//dataType

//combo[26] = new Array(4);
combo[26][0] = "126"; //j + 
combo[26][1] = "106"; //h = 
combo[26][2] = "&#2462;";	//jh
combo[26][3] = "c";	//dataType

//combo[27] = new Array(4);
combo[27][0] = "116"; //t + 
combo[27][1] = "104"; //h = 
combo[27][2] = "&#2469;";	//th
combo[27][3] = "c";	//dataType

//combo[28] = new Array(4);
combo[28][0] = "84"; //T + 
combo[28][1] = "104"; //h = 
combo[28][2] = "&#2464;";	//Th
combo[28][3] = "c";	//dataType


//combo[29] = new Array(4);
combo[29][0] = "84"; //R + 
combo[29][1] = "104"; //a = 
combo[29][2] = "&#2464;";	//Th ------- repeat ---- Ra Ru Ri fix this 
combo[29][3] = "c";	//dataType

//combo[31] = new Array(4);
combo[31][0] = "75"; //K + 
combo[31][1] = "104"; //h = 
combo[31][2] = "&#2454;";	//Kh
combo[31][3] = "c";	//dataType

//combo[32] = new Array(4);
combo[32][0] = "78"; //N + 
combo[32][1] = "106"; //j = 
combo[32][2] = "&#2462;";	//Nj
combo[32][3] = "c";	//dataType


//combo[33] = new Array(4);
combo[33][0] = "78"; //N + 
combo[33][1] = "103"; //g = 
combo[33][2] = "&#2457;";	//Ng
combo[33][3] = "c";	//dataType

/*------------- baDi MaaTraae^ ----------*/

//combo[12] = new Array(6);
combo[12][0] = "101"; 	//e + 
combo[12][1] = "101"; 	//e = 
combo[12][2] = "&#2496;";	// badi ii ki matra
combo[12][3] = "m";	//dataType
combo[12][4] = "&#2440;";	// badi ii
combo[12][5] = "v";	//dataType

//combo[13] = new Array(6);
combo[13][0] = "105"; 	//i + 
combo[13][1] = "105"; 	//i = 
combo[13][2] = "&#2496;";	// ii
combo[13][3] = "m";	//dataType
combo[13][4] = "&#2440;";	// ii
combo[13][5] = "v";	//dataType

//combo[14] = new Array(6);
combo[14][0] = "111"; 	//o + 
combo[14][1] = "111"; 	//o = 
combo[14][2] = "&#2498;";	// au ki matra
combo[14][3] = "m";	//dataType
combo[14][4] = "&#2442;";	// O
combo[14][5] = "v";	//dataType
combo[14][6] = "&#2370;";	// oo ki matra

//combo[15] = new Array(6);
combo[15][0] = "97"; 	//a + 
combo[15][1] = "105"; 	//i = 
combo[15][2] = "&#2504;";	// ai
combo[15][3] = "m";	//dataType
combo[15][4] = "&#2448;";	// ai
combo[15][5] = "v";	//dataType

//combo[16] = new Array(6);
combo[16][0] = "97"; 	//a + 
combo[16][1] = "117"; 	//a = 
combo[16][2] = "&#2508;";	// au
combo[16][3] = "m";	//dataType
combo[16][4] = "&#2452;";	// aa
combo[16][5] = "v";	//dataType

//combo[17] = new Array(6);
combo[17][0] = "97"; 	//a + 
combo[17][1] = "97"; 	//a = 
combo[17][2] = "&#2494;";	// aa
combo[17][3] = "m";	//dataType
combo[17][4] = "&#2438;";	// aa
combo[17][5] = "v";	//dataType

//combo[18] = new Array(6);
combo[18][0] = "65"; 	//A + 
combo[18][1] = "97";	//a = 
combo[18][2] = "&#2438;";	// aa
combo[18][3] = "v";	//dataType
combo[18][4] = "&#2438;";	// aa
combo[18][5] = "v";	//dataType

//combo[19] = new Array(6);
combo[19][0] = "65"; 	//a + 
combo[19][1] = "117";	//u = 
combo[19][2] = "&#2508;";	// au
combo[19][3] = "m";	//dataType
combo[19][4] = "&#2452;";	// aa
combo[19][5] = "v";	//dataType

//combo[20] = new Array(6);
combo[20][0] = "65"; 	//a + 
combo[20][1] = "105";	//i = 
combo[20][2] = "&#2448;";	// ai
combo[20][3] = "v";	//dataType
combo[20][4] = "&#2438;";	// aa
combo[20][5] = "v";	//dataType

//combo[21] = new Array(6);
combo[21][0] = "73"; 	//I + 
combo[21][1] = "105";	//i = 
combo[21][2] = "&#2496;";	// badi ii ki matra 
combo[21][3] = "m";	//dataType
combo[21][4] = "&#2440;";	// badi ee
combo[21][5] = "v";	//dataType

//combo[22] = new Array(6);
combo[22][0] = "79"; 	//O + 
combo[22][1] = "111";	//o = 
combo[22][4] = "&#2508;";	// Oo(au ki matra)
combo[22][3] = "m";	//dataType
combo[22][2] = "&#2452;";	// ( pronounce as au )
combo[22][5] = "v";	//dataType

//combo[23] = new Array(6);
combo[23][0] = "69"; 	//E + 
combo[23][1] = "101";	//e = 
combo[23][2] = "&#2503;";	// aa Matra
combo[23][3] = "m";	//dataType
combo[23][4] = "&#2447;";	// ee(aa...)
combo[23][5] = "v";	//dataType

//combo[24] = new Array(6);
combo[24][0] = "97"; 	//a + 
combo[24][1] = "97";	//a = 
combo[24][2] = "&#2494;";	// aa Matra
combo[24][3] = "m";	//dataType
combo[24][4] = "&#2438;";	// aa
combo[24][5] = "v";	//dataType

//combo[30] = new Array(6);
combo[30][0] = "79"; 	//O + 
combo[30][1] = "77";	//M = 
combo[30][2] = "&#2384;";	// OM
combo[30][3] = "v";	//dataType
combo[30][4] = "&#2384;";	// aa
combo[30][5] = "v";	//dataType

//////////////////
combo[7][0] = "117";          //u +
combo[7][1] = "117";          //u =
combo[7][2] = "&#2498;";     //bade oo ki matra
combo[7][3] = "m";            //dataType
combo[7][4] = "&#2442;";     // bada oo
combo[7][5] = "v";            //dataType

//////////////////////

} //getCharMap

/** **/
//////////////////////////////////////

public String getHexachar(int kc)
{
	if ((kc >= 48 && kc     <= 57) || (kc >= 65     && kc <= 90) || (kc     >= 97 && kc     <= 122) || (kc == 32) || (kc == 94))
	{ //kc1
		tempLkey = lastKey0;
		ErrorDumpUtil.ErrorLog("tempLkey="+tempLkey +"   lastKey0="+lastKey0+"  kc="+kc);
		ErrorDumpUtil.ErrorLog("currentChar="+currentChar+"    KC      "+(int)currentChar+"		"+uni [kc][1] +"    lastKeyType="+ lastKeyType+"   lastKeyType0="+ lastKeyType0+"      lastKeyType1="+ lastKeyType1);
		if (kc == 94    || kc == 97     || kc == 101 || kc == 105 || kc == 111 || kc == 117     || kc == 77     || kc == 72      ||     kc == 65 ||     kc == 79 || kc == 82)
		{ //kc2
			if (lastKeyType0 == "h")
			{ //if 1.1
				if (kc == 65 || kc == 79 || kc == 82) 
				{ // if 1.2 
				msg  =  uni[kc][3];
				ErrorDumpUtil.ErrorLog( "line 1045 msg>>>>>         "+msg);
				lastKeyType =  lastKeyType1;
				lastKeyType1 = lastKeyType0;
				lastKeyType0 = uni[kc][2];
				lastKey1 = lastKey0;
				lastKey0 = kc;
				} //if 1.2
				else { // else 1.2
					msg = uni[kc][1];
					
					ErrorDumpUtil.ErrorLog( "line 1054 msg>>>>>         "+msg);
					String tmp2 =  message.substring(message.length() - 5, message.length());
					ErrorDumpUtil.ErrorLog( "line 1054.1>>>>>         "+tmp2);
					if(msg == "" || tmp2.equals( "2381;"))
						message = message.substring(0, message.length() - 7);
					ErrorDumpUtil.ErrorLog( "line 1054.2 message=         "+message);
                                 } // else 1.2
				 	lastKeyType =  lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;

			} //if 1.1
			else if (lastKeyType0   == "s" || lastKeyType0 == "n"   )
                        { //else if 1      
				ErrorDumpUtil.ErrorLog( "line 1069 msg>>>>>         "+msg);
				if ((kc == 97) || (kc == 111) )
				{       
					msg  =  uni[kc][3];
					ErrorDumpUtil.ErrorLog( "line 1073 msg>>>>>         "+msg);
				 	lastKeyType =  lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][2];
                                        lastKey1 = lastKey0;
                                        lastKey0 = kc;
       		                }
				else if (kc     >= 97 )
                                {       kc = kc -       32;
                                        msg  =  uni[kc][1];
					ErrorDumpUtil.ErrorLog( "line 1082 msg>>>>>         "+msg);
				 	lastKeyType =  lastKeyType1;
                                        lastKeyType1 = lastKeyType0;
                                        lastKeyType0 = uni[kc][0];
                                        lastKey1 = lastKey0;
                                        lastKey0 = kc;
                                }
                                else
                                {
					msg  = uni[kc][1];
					ErrorDumpUtil.ErrorLog( "line 1091 msg>>>>>         "+msg);
				 	lastKeyType =  lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;
				}
			} // else if 1
			else if (lastKeyType0   == "v")
			{ //else if 2
				if (lastKeyType1 ==     "v") 
				{ // if 2.1
					msg = getCombo2(lastKey0,kc);
				 	ErrorDumpUtil.ErrorLog( "line 1099 msg>>>>>         "+msg);
					if (msg == null)
                                        {
						msg  = uni[kc][3];
				 		lastKeyType =  lastKeyType1;
						lastKeyType1 = lastKeyType0;
	                                        lastKeyType0 = uni[kc][2];
	                                        lastKey1 = lastKey0;
	                                        lastKey0 = kc;
				 		ErrorDumpUtil.ErrorLog( "line 1107 msg>>>>>         "+msg);
					}
					 else
                                        { 
                                         lastKeyType = lastKeyType1;
                                         lastKeyType1 = lastKeyType0;
                                         lastKeyType0 = "v";
                                         lastKey1 = lastKey0;
                                         lastKey0 = kc;
                                        }
				 	ErrorDumpUtil.ErrorLog( "line 1115 msg>>>>>         "+msg);
				} // if 2.1
				else if (lastKeyType1 == "s") 
				{ // else if 2.1
					msg = getCombo2(lastKey0,kc);
				ErrorDumpUtil.ErrorLog( "line 1125.11 msg>>>>>         "+msg);
                                        lastKeyType = lastKeyType1;
                                        lastKeyType1 = lastKeyType0;
                                        lastKeyType0 = "v";
                                        lastKey1 = lastKey0;
                                        lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1125 msg>>>>>         "+msg);
				} // else if 2.1
                                else if (lastKeyType1 == "h") 
				{ // else if 2.1 
					msg = getCombo(lastKey0,kc);
                                        lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1134 msg>>>>>         "+msg);
				} // else if 2.1
				
				else if (lastKeyType1 == "c") 
				{ // else if 2.1 
					msg = getCombo(lastKey0,kc);
                                        lastKeyType = lastKeyType1;
                                        lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1144  msg>>>>>         "+msg);
				} // else if 2.1
                                else if (lastKeyType1 == "n") 
				{ // else if 2.1
					msg = getCombo2(lastKey0,kc);
                                        lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = "v";
					lastKey1 = lastKey0;
					lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1154 msg>>>>>         "+msg);
				} // else if 2.1
				else if (lastKeyType1 == "m") 
				{ // else if 2.1
					msg = getCombo2(lastKey0,kc);
					if (msg == null)
					{
						msg = uni[kc][3];
                                        	lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][2];
						lastKey1 = lastKey0;
						lastKey0 = kc;}
					else
					{
                                        	lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = "v";
						lastKey1 = lastKey0;
						lastKey0 = kc;
					}
				ErrorDumpUtil.ErrorLog( "line 1172 msg>>>>>         "+msg);
				}// else if 2.1
				else 
				{  //else of else if 2.1
					msg = getCombo(lastKey0,kc);
                                       	lastKeyType = lastKeyType1;
                                        lastKeyType1 = lastKeyType0;
                                        lastKeyType0 = uni[kc][0];
                                        lastKey1 = lastKey0;
                                        lastKey0 = kc;  
				ErrorDumpUtil.ErrorLog( "line 1182 msg>>>>>         "+msg);
				}
				if (msg == uni[kc][1])                                
				{
					if ((kc == 97) || (kc == 111))
                                        {
						msg  = uni[kc][3];
                                       		lastKeyType = lastKeyType1;
						lastKeyType0 = uni[kc][2];
                                        }
					else if (kc     >= 97 )
					{
						if(kc != 117)
						{
							kc      = kc - 32;
	                                                msg  = uni[kc][1];
        	                               		lastKeyType = lastKeyType1;
                	                                lastKeyType0 = uni[kc][0];
                        	                        lastKey0 = kc;
						}
					}
				ErrorDumpUtil.ErrorLog( "line 1197 msg>>>>>         "+msg);
				}
			 ErrorDumpUtil.ErrorLog( "line 1203 msg>>>>>         "+msg);
			} //else if 2
			else if (lastKeyType0   == "m")
			{  //else if 3
				ErrorDumpUtil.ErrorLog( "line 1202 >>>>>");
				if      (lastKeyType1   ==	"v") 
				{ //  if 3.1
					msg = getCombo(lastKey0,kc);
                                       	lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1211 msg>>>>>         "+msg);
				}
				else if (lastKeyType1 ==        "s") 
				{  //else if 3.1
					msg    = getCombo(lastKey0,kc);
					if (msg == null)
					{
						msg  = uni[kc][3];
                                       		lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][2];
						lastKey1 = lastKey0;
						lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1223 msg>>>>>         "+msg);
					}
					else
					{
                                       		lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][0];
						lastKey1 = lastKey0;
						lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1231 msg>>>>>         "+msg);
					}
				} //else if 3.1
				else if (lastKeyType1 ==        "h") 
				{ //else if 3.1
                                        msg = getCombo(lastKey0,kc);
					ErrorDumpUtil.ErrorLog( "line 1236 msg>>>>>         "+msg);
                                        if (msg == null)
					{
						msg  = uni[kc][3];
                                       		lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][2];
						lastKey1 = lastKey0;
						lastKey0 = kc;
                                        }
					else 
					{ 
                                       		lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][0];
						lastKey1 = lastKey0;
						lastKey0 = kc;
					}
					ErrorDumpUtil.ErrorLog( "line 1252 msg>>>>>         "+msg);
				} //else if 3.1
				else if (lastKeyType1 ==        "c") 
				{//else if 3.1  
					msg    = getCombo(lastKey0,kc);
					ErrorDumpUtil.ErrorLog( "line 1257 msg>>>>>         "+msg);
                               		lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;  
				} //else if 3.1
				else if (lastKeyType1 ==	"m") 
				{ //else if 3.1
					ErrorDumpUtil.ErrorLog( "line 1265 msg>>>>>         "+msg);
					if (getCombo(lastKey0,kc) == null)
					{
						if (uni[kc][3] == null) 
						{ 
							msg  = uni[kc][1];
                               				lastKeyType = lastKeyType1;
							lastKeyType1 = lastKeyType0;
							lastKeyType0 = uni[kc][0];
							lastKey1 = lastKey0;
							lastKey0 = kc;
							ErrorDumpUtil.ErrorLog( "line 1275 msg>>>>>         "+msg);
						}
						else {
							msg  = uni[kc][3];
                               				lastKeyType = lastKeyType1;
							lastKeyType1 = lastKeyType0;
							lastKeyType0 = uni[kc][2];
							lastKey1 = lastKey0;
							lastKey0 = kc;
							ErrorDumpUtil.ErrorLog( "line 1283 msg>>>>>         "+msg);
						}
					}
					else { 
						msg  = uni[kc][3];
                               			lastKeyType = lastKeyType1;
						lastKeyType1 = lastKeyType0;
						lastKeyType0 = uni[kc][2];
						lastKey1 = lastKey0;
						lastKey0 = kc; 
						ErrorDumpUtil.ErrorLog( "line 1292 msg>>>>>         "+msg);
					}
				} //else if 3.1
				else {	
					msg = getCombo(lastKey0,kc); 
					ErrorDumpUtil.ErrorLog( "line 1297 msg>>>>>         "+msg);
                               		lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;	
				}
				
				if	(msg == uni[kc][1])
				{	
					if ((kc	== 97) || (kc == 111))
					{ 
						msg  = uni[kc][1];
						ErrorDumpUtil.ErrorLog( "line 1309 msg>>>>>         "+msg);
					}
					else if	(kc	>= 97 )	
					{
						 kc = kc -	32;
					}
						msg  = uni[kc][1];
                               			lastKeyType = lastKeyType1;
						lastKeyType0 = uni[kc][0];
						lastKey0 = kc;
						ErrorDumpUtil.ErrorLog( "line 1318 msg>>>>>         "+msg);
				}
			} //else if 3
				
			else if(lastKeyType1 == "c")	
			{ //else if 4
				msg = getCombo(lastKey0,kc);
                       		lastKeyType = lastKeyType1;
				lastKeyType1 = lastKeyType0;
				lastKeyType0 = uni[kc][0];
				lastKey1 = lastKey0;
				lastKey0 = kc;
				ErrorDumpUtil.ErrorLog( "line 1329 msg>>>>>         "+msg);
			}
			else 
			{ //else of else if 4 
				if ((kc	== 97) || (kc == 111))
				{
					msg  = uni[kc][3]; 	
                       			lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][2];
					lastKey1 = lastKey0;
					lastKey0 = kc;	
					ErrorDumpUtil.ErrorLog( "line 1340 msg>>>>>         "+msg);
				}
				else if	(kc	>= 97 )	
				{ 
					kc = kc -	32;
					msg  = uni[kc][1];
                       			lastKeyType = lastKeyType1;
					lastKeyType1 = lastKeyType0;
					lastKeyType0 = uni[kc][0];
					lastKey1 = lastKey0;
					lastKey0 = kc;	
					ErrorDumpUtil.ErrorLog( "line 1350 msg>>>>>         "+msg);
				}
			} //else of else if 4
		} // kc2
		else
		{ // else of kc2
			ErrorDumpUtil.ErrorLog( "line 1418 msg>>>>>         ");
			if (kc == 32){
				if (lastKeyType0 == "h") 
					msg  = uni[kc][1];
				else
					msg  = uni[kc][1];
				lastKeyType0 = uni[kc][0];
				lastKey0 = kc;
			}
			else if	(uni[kc][0]	== "c")	
			{ //else if else of kc2
				ErrorDumpUtil.ErrorLog( "line 1429 msg>>>>>       uni[kc][0]  ="+uni[kc][0] +".....lstKeyType0="+lastKeyType0);
				if (lastKeyType0 == "h" || lastKeyType0	== "c")
				{	
					ErrorDumpUtil.ErrorLog( "line 1432 msg>>>>>         ");
					ErrorDumpUtil.ErrorLog( "line 1433 msg>>>>>"+getCombo(lastKey0,kc));
					msg = getCombo(lastKey0,kc);
					ErrorDumpUtil.ErrorLog( "line 1435 msg>>>>>"+msg);
					if (msg == null )
					{ 
						msg     = uni[kc][1];
						ErrorDumpUtil.ErrorLog( "line 1437 msg>>>>>         "+msg+"......uni[kc][1]"+uni[kc][1]);
					}
					if (msg == uni[kc][1])
					{
						msg     = uni[kc][1]+"&#2381;";
                       				lastKeyType = lastKeyType1;
						lastKeyType0	= "h";
						lastKey0	= kc;
						ErrorDumpUtil.ErrorLog( "line 1445 msg>>>>>         "+msg);
					}
					else {
						message = message.substring(0, message.length() - 14);
						msg  =  msg +"&#2381;";
                       				lastKeyType = lastKeyType1;
						lastKeyType0	= "h";
						lastKey0	= kc;
					ErrorDumpUtil.ErrorLog( "line 1386 msg>>>>>         "+msg);
					}
					ErrorDumpUtil.ErrorLog( "line 1370 msg>>>>>         "+msg);
				}
				else 
				{	
					ErrorDumpUtil.ErrorLog( "line 1454 msg>>>>>         ");
					msg     = uni[kc][1]+"&#2381;";
               				lastKeyType = lastKeyType1;
					lastKeyType0 = "h";
					lastKey0 = kc;
					ErrorDumpUtil.ErrorLog( "line 1459 msg>>>>>         "+msg);
				}
				ErrorDumpUtil.ErrorLog("line 1391..."+msg);
			} //else if else of kc2
			else if	(uni[kc][0]	== "s" || uni[kc][0] ==	"n") 
			{
				if( uni[kc][0] == "n")
					msg	= "&#"+uni[kc][1]+";";
				else
					msg	= uni[kc][1];
       				lastKeyType = lastKeyType1;
				lastKeyType0 =	uni[kc][0];
				ErrorDumpUtil.ErrorLog("\n msg.....line 1400...for numeric"+msg);
			}
			else
			{   
				msg = uni[kc][1];
       				lastKeyType = lastKeyType1;
				lastKeyType0 =	uni[kc][0];
				ErrorDumpUtil.ErrorLog("\n msg......ne 1406..for numeric"+msg);
			} 
			ErrorDumpUtil.ErrorLog("line 1408..."+msg);
		} // else of kc2
		
	} // kc1
	else
	{
		msg = Integer.toString(kc);
		lastKeyType = lastKeyType1;
		lastKeyType0	= "s";
		ErrorDumpUtil.ErrorLog("\n msg........inutil  1469 line ="+msg);
	}
	ErrorDumpUtil.ErrorLog("msg....line 1487....."+msg);
	message = message +msg;
	ErrorDumpUtil.ErrorLog("message.....line 1480....."+message);
	return(message);
}
public String getCombo2(int lKey, int cKey)
{
	int i;
	int j;
	String   d = ""; 
	boolean flag = true;
	if(lKey == 85)
	{
		lKey = lKey + 32;
	}		
	ErrorDumpUtil.ErrorLog("Integer.toString(cKey)"+Integer.toString(cKey)+"  Integer.toString(lKey)="+Integer.toString(lKey));
        for(i=0;i<=33;i++)
	{
	//	ErrorDumpUtil.ErrorLog("message="+message+"i="+i+"  getcombo2  combo[i][0]="+combo[i][0]+"     combo[i][1]="+combo[i][1]);
		if ((combo[i][0]).equals(Integer.toString(lKey))&& (combo[i][1]).equals(Integer.toString(cKey)))
		{
			ErrorDumpUtil.ErrorLog("message="+message+"i="+i+"  getcombo2  combo[i][0]="+combo[i][0]+"     combo[i][1]="+combo[i][1]);
			if ((combo[i][0]).equals("97")&& (combo[i][1]).equals("105") || (combo[i][0]).equals("65")&& (combo[i][1]).equals("105"))
			{
				String str =  message.substring(message.length() - 7, message.length());
				ErrorDumpUtil.ErrorLog("message="+message+"  getcombo2 str="+str);
				if(str.equals("&#2309;"))
				{
					message = message.substring(0, message.length() - 7);
					d = combo[i][4]; 
				}
				if(str.equals("&#2366;") || str.equals("&#2310;"))
					d= uni [cKey][3];
			}
			else if((combo[i][0]).equals("105") && (combo[i][1]).equals("105") || (combo[i][0]).equals("73") && (combo[i][1]).equals("105"))
			{
				message = message.substring(0, message.length() - 7);
				d = combo[i][4]; 
			}
	 		else if((combo[i][0]).equals("111") && (combo[i][1]).equals("111"))
			{
				message = message.substring(0, message.length() - 7);
				d = combo[i][4]; 
				ErrorDumpUtil.ErrorLog("d="+d+"  getcombo2   oo  ");
			}
	 		else if((combo[i][0]).equals("117") && (combo[i][1]).equals("117"))
			{
				message = message.substring(0, message.length() - 7);
				d = combo[i][4]; 
				ErrorDumpUtil.ErrorLog("d="+d+"  getcombo2   uu  ");
			}
			else
				d = combo[i][2]; 
				ErrorDumpUtil.ErrorLog("d="+d+"  getcombo2   else  ");

			flag = false;
			break;
                }
        }
	if(flag)
		d= uni [cKey][1];
	ErrorDumpUtil.ErrorLog("d="+d+"  getcombo2   flag  "+flag);
	return  d;
}
public String getCombo(int lKey,int cKey)
{
///////////////////////////////////////////////
	int i=0;
        int j=0;
        String d = "";
	boolean flag = true;
	//System.out.println("Integer.toString(lKey).."+Integer.toString(lKey)+"Integer.toString(cKey).."+Integer.toString(cKey));
        for     (i=0; i<=33; i++)
	{
		//ErrorDumpUtil.ErrorLog("  getcombo  message  i="+i+"....combo[i][0]........="+combo[i][0]+"...lKey="+lKey+"......combo[i][1]="+combo[i][1]+"..cKey ="+cKey);
		if ((combo[i][0]).equals(Integer.toString(lKey)) && (combo[i][1]).equals(Integer.toString(cKey)))
                {
			//ErrorDumpUtil.ErrorLog("  getcombo  message =  "+message);
			if((combo[i][0]).equals("105") && (combo[i][1]).equals("105") || (combo[i][0]).equals("73") && (combo[i][1]).equals("105")) // for i & i  OR I & i
			{
				message = message.substring(0, message.length() - 7);
				//ErrorDumpUtil.ErrorLog("  getcombo  message1 =  "+message);
				d = combo[i][2]; 
			}
	 		else if((combo[i][0]).equals("111") && (combo[i][1]).equals("111")) // o & o
			{
				message = message.substring(0, message.length() - 7);
				d=combo[i][6];
				//ErrorDumpUtil.ErrorLog("d="+d+"  getcombo   oo  ");
			}
			else if ((combo[i][0]).equals(Integer.toString(lKey)) && (combo[i][1]).equals(Integer.toString(cKey)) && (combo[i][4]).equals(Integer.toString(tempLkey)))
			{
				d=combo[i][2];
				tempLkey = 0;
				//ErrorDumpUtil.ErrorLog("  getcombo  line 1593 =  "+d);
			}
			else{	d=combo[i][2]; }
			//ErrorDumpUtil.ErrorLog("message.........."+message+"  getcombo    1597     d    ......  "+d);
			flag=false; 
			break;
		}
		//ErrorDumpUtil.ErrorLog("  getcombo  line 1601 d=  "+d +"i "+i);
	}	
	if(flag)
	{
		d= uni [cKey][1];
		//ErrorDumpUtil.ErrorLog("  getcombo  1602  "+d);
		
	}
	//ErrorDumpUtil.ErrorLog("  getcombo 1605  ");
	return d;
}

/* public static void main(String args[])
 {
   Roman2Unicode r2u = new  Roman2Unicode();
    
    r2u.getstrUnicode("aap","hindi");
 }*/
}//class ISCII2Unicode
