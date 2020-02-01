/**
 * Token class used to cut string into tokens and return array of those tokens
 */

public class Token {


    /** Instance variable str  */
    private final String str;


    /**
     * Constructor of the class
     * @param str
     */
    public Token(String str){
        this.str = str;
    }






    /**
     * getTokens method receives string as a parameter and returns all the word in that string as an array
     * @param str
     * @return
     */
    public String[] getTokens(String str){

        String[] tokenArray = new String[0];                                                                            /** New array with the size 0 */

        for(int pos = 0; pos < str.length();){

            pos = skipSpaces(str, pos);                                                                                 /** Find non-empty space */
            String token = nextToken(str, pos);                                                                         /** get one token */

            if(token.length() != 0) {                                                                                   /** check for empty string */

                String[] newTokenArray = new String[tokenArray.length + 1];                                             /** Create a new array with size tokenArray.length + 1 */

                for(int i = 0; i < tokenArray.length; i++)                                                              /** Copy everything from tokenArray to a new array */
                    newTokenArray[i] = tokenArray[i];

                newTokenArray[newTokenArray.length - 1] = token;                                                        /** Put the token to the end of the new array */

                tokenArray = newTokenArray;                                                                             /** Copy everything from new array back to the tokenArray */

            }

            pos += token.length();                                                                                      /** Assign new position */

        }

        return tokenArray;                                                                                              /** Return array of tokens */
    }




    /**
     * Method that is used to skip empty spaces in the string
     * receives string and position as parameters
     * returns the position of the next non-empty space
     * @param str
     * @param pos
     * @return
     */
    public int skipSpaces(String str, int pos){

        for(; pos < str.length(); pos++){

            if(str.charAt(pos) != ' ')
                break;

        }

        return pos;

    }




    /**
     * Method that is used to skip non-empty spaces in the string
     * receives string and position as parameters
     * returns the position of the next empty space
     * @param str
     * @param pos
     * @return
     */
    public String nextToken(String str, int pos){

        String token = "";

        for(; pos < str.length(); pos++){

            if(str.charAt(pos) == ' ')
                break;

            token += str.charAt(pos);

        }

        return token;

    }

}
