package sample;

import java.io.*;
import java.util.ArrayList;

public class RoomList implements Serializable, FileIO {
    private ArrayList<Room> rooms;

    public RoomList()
    {
        this.rooms = new ArrayList<Room>();
    }

    public void addRoom(Room room)
    {
        this.rooms.add(room);
    }

    public void removeRoom(Room room)
    {
        for(int i = 0; i < rooms.size(); i++)
        {
            if(rooms.get(i).equals(room))
                rooms.remove(i);
        }

    }

    public Room getRoom(String id)
    {
        for(int i = 0; i < rooms.size(); i++)
        {
            if(rooms.get(i).getId().equals(id))
                return rooms.get(i);
        }

        return null;
    }

    public void removeRoom(String id)
    {
        for(int i = 0; i < rooms.size(); i++)
        {
            if(rooms.get(i).getId().equals(id))
                rooms.remove(i);
        }
    }

    public void addRoom(int capacity, String id)
    {
        Room aux = new Room(capacity, id);
        this.rooms.add(aux);
    }

    public int getSize()
    {
        return rooms.size();
    }

    public ArrayList<Room> getAvailableRooms()
    {
        ArrayList<Room> availableRooms = new ArrayList<Room>();
        for(int i = 0; i < rooms.size(); i++)
        {
            if(rooms.get(i).isAvailable())
                availableRooms.add(rooms.get(i));
        }
        return availableRooms;
    }

    public ArrayList<Room> getAllRooms()
    {
          return (ArrayList)rooms.clone();
    }

    @Override public void saveToBinaryFile()
    {
        ObjectOutputStream out = null;
        try
        {
            File file = new File("roomData.bin");
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(rooms);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert out != null;
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override public void loadFromBinaryFile(String filePath)
    {
        ObjectInputStream in = null;
        try
        {
            File file = new File(filePath);
            InputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);

            rooms = (ArrayList<Room>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
