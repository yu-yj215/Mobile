package com.example.finalproject;
import java.util.ArrayList;
import androidx.lifecycle.ViewModel;

public class Itemstorage extends ViewModel {
    private ArrayList<Item> itemList = new ArrayList<>();

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}
