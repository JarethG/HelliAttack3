package helpz;

import objects.enemies.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TOC {
    public static final int[] TRAINING_BLOCK_IMAGES = new int[]{1000, 1002, 1004, 1006, 1008, 1010, 1012, 1014, 1016, 1018, 1020, 1022, 1024, 1026, 1028, 1030, 1032, 1034, 1036, 1038, 1040, 1042, 1044, 1046, 1048, 1050, 1052, 1054, 1056, 1058, 1060, 1062, 1064, 1066, 1068, 1070, 940, 942, 944, 946, 948, 950, 952, 954, 956, 958, 960, 962, 964, 966, 968, 970, 972, 974, 976, 978, 980, 982, 984, 986, 988, 990, 992, 994, 996, 998};
    public static final int[] SNOW_BLOCK_IMAGES = new int[]{1352,1354,1356,1358,1360,1362,1364,1366,1368,1370,1372,1374,1376,1378,1380,1382,1384,1386,1388,1390,1392,1394,1396,1398,1400,1402,1404,1406,1408,1410,1412,1414,1416,1418,1420,1422,1424,1426,1428,1430,1432,1434,1436,1438,1440,1442,1444,1446,1448,1450,1452,1454,1456,1458,1460,1462,1464,1466,1468,1470,1472,1474,1476,1478,1480,1482,1484,1486,1488,1490,1492,1494,1496,1498,1500,1502,1504,1506,1508,1510,1512,1514,1516,1518,1520,1522,1524,1526,1528,1530,1532,1534,1536,1538,1540,1542,1544,1546,1548,1550,1552,1554,1556,1558,1560,1562,1564,1566,1568,1570,1572,1574,1576,1578,1580,1582,1584,1586,1588,1590,1592,1594,1596,1598};
    public static final int[] FOREST_BLOCK_IMAGES = new int[]{1103, 1105, 1107, 1109, 1111, 1113, 1115, 1117, 1119, 1121, 1123, 1125, 1127, 1129, 1131, 1133, 1135, 1137, 1139, 1141, 1143, 1145, 1147, 1149, 1151, 1153, 1155, 1157, 1159, 1161, 1163, 1165, 1167, 1169, 1171, 1173, 1175, 1177, 1179, 1181, 1183, 1185, 1187, 1189, 1191, 1193, 1195, 1197, 1199, 1201, 1203, 1205, 1207, 1209, 1211, 1213, 1215, 1217, 1219, 1221, 1223, 1225, 1227, 1229, 1231, 1233, 1235, 1237, 1239, 1241, 1243, 1245, 1247, 1249, 1251, 1253, 1255, 1257, 1259, 1261, 1263, 1265, 1267, 1269, 1271, 1273, 1275, 1277, 1279, 1281, 1283, 1285, 1287, 1289, 1291, 1293, 1295, 1297, 1299, 1301, 1303, 1305, 1307, 1309, 1311, 1313, 1315, 1317, 1319, 1321, 1323, 1325, 1327, 1329, 1331, 1333, 1335, 1337, 1339, 1341, 1343, 1345, 1347};
    public static final int[] DESERT_BLOCK_IMAGES = new int[]{665,667,669,671,673,675,677,679,681,683,685,687,689,691,693,695,697,699,701,703,705,707,709,711,713,715,717,719,721,723,725,727,729,731,733,735,737,739,741,743,745,747,749,751,753,755,757,759,761,763,765,767,769,771,773,775,777,779,781,783,785,787,789,791,793,795,797,799,801,803,805,807,809,811,813,815,817,819,821,823,825,827,829,831,833,835,837,839,841,843,845,847,849,851,853,855,857,859,861,863,865,867,869,871,873,875,877,879,881,883,885,887,889,891,893,895,897,899,901,903,905,907,909,911,913,915,917,919,921,923,925,927,929,931,933,935,937,};
    public static final int[] AMMO_IMAGES = new int[]{196,325,327,329,331,333,335,337,339,341,343,345,347,350,352,354,356,359,361,363,365,367,368,399,401,403,405,407,409,411,413,415,417,419,421,423,425,427,429,431,433,435,437,439,441,443,445,447,449,451,453,455,457,459,461,463,465,};
    public static final int[] PLAYER_IMAGES = new int[]{104,106,109,111,113,115,117,119,121,123,127,129,131,133,135,137,139,142,144,147,149,151,153,158,160,163,165,167,169,172,174,176,178,180,182,184,186,187,189,191,193,195,198,202,204,206,208,210,213,215,217,219,221,223,228,232,235,237,241,243,245,247,249,251,253,255,260,262,264,266,268,270,272,274,276,278,280,282,284,286,288,290,292,294};
    public static final int[] ENEMY_IMAGES = new int[]{1093,1095,1099,1802,1804,1806,311,315,317,321,593,595,597,599,601,603,605,607,610,612,614,616,618,620,624,629,632,636,637,640,642,649,652,};
    public static final int[] BACKGROUND_IMAGES = new int[]{305,308,1601,1604};
    public static final int[] EXPLOSION_IMAGES = new int[]{371,373,375,377,379,381,383,385,387,389,391,393,395};
    public static final int[] UI_IMAGES = new int[]{1664,83,1646,470,472,474,480,489,491,493,499,520,399,401,403,405,407,409,411,413,415,417,419,421,423,425,427,429,431,433,435,437,439,441,443,445,447,449,451,453,455,457,459,461,463,465};

    public static final int[] STARTING_UNLOCKS = new int[]{147};

    public static int[][] trainingZoneButtonIds = new int[][]{
            {940, 942, 944, 946, 948, 950},
            {952,954,956,958},
            {960,962,964,966,968,970,972,974,976},
            {978,980,982,984,986},
            {992,994,996,998,1042,1044,1046,1048},
            {1000,1002,1004,1006,1008,1010,1012,1014,1016,1022,1024,1026,1028,1030,1032,1034,1040},
            {1050,1052,1054,1056,1058,1060,1062,1064},
            {1066,1068,1070}
    };

    public static int[][] snowZoneButtonIds = new int[][]{
            {1352,1354,1356,1358,1360,1362,1364,1366,1368,1370,1386,1388,1390,1392,1434,1444},//corner
            {1372,1374,1376,1378,1380,1382,1384,1394,1396,1398,1400,1402,1404,1406,1408,1410,//edges
                    1412,1414,1416,1418,1420,1422,1424,1426,1428,1430,1432,1442,1446,1472,1474,1476,1478,1480,1482},
            {1436,1438,1440,1560},//whole
            {1448,1450,1452,1454,1456,1458,1460,1576,1578,1580,1548,1550},//tree
            {1462,1464,1466,1468,1470},//roots
            {1490,1492,1494,1496,1498,1500,1502,1504},//whole stone
            {1518,1520,1522,1524,1526,1528},//wood
            {1484,1486,1488,1506,1508,1510,1512,1514,1516,1530,1532,1534,1536,1538,1540,1542,1544,1546,1552,1554,1556,1558,1562,1564,1566,1568,1570,1572,1574,1582,1584,1586,1588,1590,1592,1594,1596,1598,1601,1604}
    };
    public static int[][] forestZoneButtonIds = new int[][]{
            {1105,1107,1109,1111,1113,1115,1117,1119,1121,1123,1139,1145,1205,},//corner
            {1125,1127,1129,1153,1155,1157,1167,1173,},///bridge
            {1103,1131,1133,1135,1137,1147,1149,1151,},//whole
            {1141,1143,},//
            {1159,1161,1163,1165,1169,1171,1175,1177,1179,1181,1183,1185,1187,1189,1191,1193,1195,1197,1199,1201,1203,1207,1215,1217,1219,1221,1223,1225,1227,1251,},//wood
            {1209,1211,1213,1229,1231,1233,1235,1237,1239,1241,1243,1245,1247,1249,1253,1255,1257,1259,1261,1263,1265,1267,1269,1271,1273,1275,1277,1279,1281,1283,1285,1287,1289,},//leaves
            {1291,1293,1295,1297,1299,1301,1303,1305,1307,1309,1311,1313,1315,1317,1319,1321,1323,1325,1327,1329,1331,1333,1335,1337,1339,1341,1343,1345,1347,
            }
    };
    public static int[][] desertZoneButtonIds = new int[][]{
            {665,667,669,671,673,675,},//corner
            {841,843,845,847,849,851,853,},///bridge
            {689,691,693,695,697,699,701,703,705,707,709,711,713,715,717,719,721,723,725,731,733,},//whole
            {677,679,681,683,685,687,727,729,735,737,739,741,743,745,747,749,751,753,755,757,759,761,763,765,767,769,771,773,775,777,779,781,783,785,787,789,791,793,795,831,833,},//edge
            {797,799,801,803,805,807,809,811,813,815,817,819,821,823,825,827,829},//climb
            {835,837,839,},//pillars
            {841,843,845,847,849,851,853,855,857,859,861,863,865,867,869,871,873,875,877,879,881,883,885,887,889,891,893,895,897,899,901,903,905,907,909,911,913,915,917,919,921,923,925,927,929,931,933,935,937}//
    };

    public static  int[][] weaponUnlocks = new int[][]{
    };
    public  static ArrayList<Enemy> getLevelEnemies(int zone, int level) {
        if(zone==0){
            return new ArrayList<>();
        } else if(zone == 1){
            switch (level){
                case 1:  return generateWave(1,6);
                case 2:  return generateWave(1,9);
                case 3:  return generateWave(1,12);
                case 4:  return generateWave(2,6);
            }
        }else if(zone == 2){
            switch (level){
                case 1:  return generateWave(2,8);
                case 2:  return generateWave(2,10);
                case 3:  return generateWave(2,12);
                case 4:  return generateWave(3,6);
            }
        }else if(zone == 3){
            switch (level){
                case 1:  return generateWave(1,12);
                case 2:  return generateWave(2,12);
                case 3:  return generateWave(3,8);
                case 4:  return generateWave(4,6);
            }
        }else if(zone == 4){
            switch (level){
                case 1:  return generateWave(2,14);
                case 2:  return generateWave(3,10);
                case 3:  return generateWave(3,8);
                case 4:  return generateWave(2,100);
            }
        }
        return new ArrayList<>();
    }

    public static final int[] WEAPONS = new int[]{195,149,151,153,158,202,176,163,182,184,215,169,165,219,206,208,180,221,223,178};
    public static final int[] UNIMPLEMENTED = new int[]{147,174,187,189,217,204,186,213,191};

    public static final int[] UNLOCK_ORDERED = new int[]{142,195,149,151,153,158,202,163,174,176,182,184,187,169,180,215,165,219,189,217,204,206,208,221,193,213,223,178,191};

    public  static int[] getWeaponUnlocks(int zone, int level) {
        if(zone==0){
            return UNLOCK_ORDERED;//secret weapon flak ontop. chaingun hidden
        } else if(zone == 1){
            switch (level){
                case 1: return getSubArray(2);//unlock bow //machine gun
                case 2: return getSubArray(4);//unlock chaingun //shotgun
                case 3: return getSubArray(6);//unlock double shotgun
                case 4: return getSubArray(7);//unlock Sniper rifle //grenade launcher //rocket
            }
        }else if(zone == 2){
            switch (level){
                case 1: return getSubArray(10);// drunken launcher ,rpg,seeker,guided,
                case 2: return getSubArray(13);// shotgun rockets
                case 3: return getSubArray(15);//bladerang, flak cannon flamethrower, rail gun
                case 4: return getSubArray(19);//unlock laserRifle, spark gun, goo gunn
            }
        }else if(zone == 3){
            switch (level){
                case 1: return getSubArray(22);//unlock autolaserRifle
                case 2: return getSubArray(23);//unlock shotgun laser
                case 3: return getSubArray(24);// unlock any time, airstrike
                case 4: return getSubArray(26);//unlock  taser
            }
        }else if(zone == 4){
            switch (level){
                case 1: return getSubArray(27);//unlock soundwave
                case 2: return getSubArray(28);//unlock abomb
                case 3: return getSubArray(29);//unlock blackhole
                case 4: return getSubArray(30);//unlock nothing
            }
        }
        return new int[] {};
    }

    private static int[] getSubArray(int i) {
        int[] sub = new int [i];
        for(int j = 0; j < i; j++){
            sub[j] = UNLOCK_ORDERED[j];
        }
        return sub;
    }

    private static ArrayList<Enemy> generateWave(int type, int num) {
        ArrayList<Enemy> wave = new ArrayList<>();
        for(int i = 0; i < num; i ++ ) {
            if(type==1)
                wave.add(new Drone());
            else if(type==2)
                wave.add(new Helicopter());
            else if(type==3)
                wave.add(new Bomber());
            else if(type==4)
                wave.add(new AttackCopter());
        }
        return wave;
    }

    public static void getNames(String name) {
        String filename = name;
        Path path = null;//path to directory
        try {
            path = Paths.get(ImageLoader.class.getClassLoader().getResource(filename).toURI());
            List<Path> paths = findByFileExtension(path, ".png");//list of paths to images in directory
            for (Path p : paths) {
                String imageName = removeExtension(p.getFileName().toString(), ".png");
                System.out.print(imageName + ",");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Path> findByFileExtension(Path path, String fileExtension) {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path : " + path + " is not a directory path");
        }

        List<Path> result = null;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String removeExtension(String string, String extension) {
        return string.substring(0, string.lastIndexOf(extension));
    }
}
