package com.game;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadMap {

    public static Map loadMapFromFile(FileHandle file) {
        if (!file.exists()) {
            System.out.println(file + " doesn't exist!");
            return null;
        }

        String fileData;

        try {
            fileData = file.readString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String mapName;
        ArrayList<Entity> mapEntities = new ArrayList<>();
        Tile[][] mapTiles;

        try {
            String[] lines = fileData.split("\\R+");

            mapName = lines[0];
            mapTiles = new Tile[Integer.parseInt(lines[1])][Integer.parseInt(lines[2])];

            int i = 3;
            while (i < lines.length) {
                if (lines[i].equals("entities")) {
                    i++;

                    String[] data;
                    HashMap<String, Integer> properties;
                    String[] propertyData;

                    while (!lines[i].equals("/entities")) { //Read lines until a line says "/entities"
                        data = lines[i].split("/");

                        String[] base = data[0].split("\\.");
                        Entity newEntity = Load.getEntityFromID(base[0], Integer.parseInt(base[1]), Integer.parseInt(base[2]));

                        if (newEntity != null) {
                            mapEntities.add(newEntity);

                            //Add properties
                            for (int p = 1; p < data.length; p++) {
                                propertyData = data[p].split(":");
                                newEntity.addProperty(propertyData[0], propertyData[1]);
                            }
                        }

                        i++;
                    }

                    i++;
                    continue; //We don't need to check this line again
                }

                if (lines[i].equals("tiles")) {
                    i++;

                    int y = 0;
                    String[] row;

                    //Read lines until a line says "/tiles"
                    while (!lines[i].equals("/tiles") && y < mapTiles.length) {
                        row = lines[i].split(",");

                        for (int x = 0; x < row.length; x++) { //Read all tiles in a row
                            /*if (row[x].contains("/")) { //Check whether there are properties that need additional parsing
                                String[] tileData = row[x].split("/");
                                mapTiles[y][x] = Load.getTileFromID(tileData[0]);

                                if (mapTiles[y][x] == null) {
                                    System.out.println("Invalid tile ID at " + x + ", " + y + " in " + file);
                                    return null;
                                }

                                //Add properties
                                for (int p = 1; p < tileData.length; p++) {
                                    String[] propertyData = tileData[p].split(":");
                                    mapTiles[y][x].setProperty(propertyData[0], Integer.parseInt(propertyData[1]));
                                }
                            } else {*/
                            mapTiles[y][x] = Load.getTileFromID(row[x]);

                            if (mapTiles[y][x] == null) {
                                System.out.println("Invalid tile ID at " + x + ", " + y + " in " + file);
                                return null;
                            }
                            //}
                        }

                        y++;
                        i++;
                    }

                    i++;
                    continue; //We don't need to check this line again
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(file + " is not a valid map file.");
            e.printStackTrace();
            return null;
        }

        return new Map(mapName, mapTiles, mapEntities);
    }
}
