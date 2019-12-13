package Cache;

import java.io.File;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CacheHandler extends Thread {

    private ServerSocket ss;
    private static int total = 3;//the total file number that we can cache
    private static String clock[][] = new String[2][total];//first row is name of file, second is the flag to indicate whether this file used or not

//    public void updateCache(String file, int flagFileExist) {
//
//        //using clock algorithm to cache
//        //if the file exists in the cache, mark it to indicate this file is read
//        if (flagFileExist == 1) {
//            //the file is in local, make the clock to 1
//            for (int i = 0; i < total; i++) {
//                if (clock[0][i].contentEquals(file)) {
//                    clock[1][i] = "1";
//                }
//            }
//        } else {//the file is download from remote server
//            //need to put the file in clock array
//            int j = 0;
//            for (; j < total; j++) {
//                if (clock[0][j] == null) {//there is enough room for the file, put the file into data structure
//                    clock[0][j] = file;
//                    clock[1][j] = "1";
//                    break;
//                }
//            }
//            if (j == total) {
//                //there is no room, need to replace one file in clock
//                int r = 0;
//                for (; r < total; r++) {
//                    if (clock[1][r] == "0") {//replace with this
//                        clock[0][r] = file;
//                        clock[1][r] = "1";
//                        break;
//                    } else {//change it to 0
//                        clock[1][r] = "0";
//                    }
//                }
//                if (r == total) {
//                    //the first iteration cannot find a file to replace, then it must replace with the first file because it is marked 0
//                    String fileName = clock[0][0];
//                    File delFile = new File(fileName);
//                    delFile.delete();
//                    clock[0][0] = file;
//                    clock[1][0] = "1";
//
//                }
//            }
//        }
//        //print the content of clock array
//        for (int s = 0; s < total; s++) {
//            if (clock[0][s] != null) {
//                System.out.println("== file: " + clock[0][s] + " == clock: " + clock[1][s] + " ==");
//            }
//        }
//    }
    Queue<String > queue = new LinkedList<String>();
    int cacheSize = 3;
    public void updateCache(String fileName) {
        queue.add(fileName);
        if(queue.size() > cacheSize) {
            String deletedFileName = queue.remove();
            File delFile = new File(deletedFileName);
            delFile.delete();

        }

    }

}