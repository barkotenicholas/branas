package com.demo.productcatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class ProductModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int productId;
    private String name;
    private String model;
    private String category;
    private String style;
    private String color;
    private float price;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductModel(int productId, String name, String model, String category, String style, String color, float price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.model = model;
        this.category = category;
        this.style = style;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    protected ProductModel(Parcel in) {
        productId = in.readInt();
        name = in.readString();
        model = in.readString();
        category = in.readString();
        style = in.readString();
        color = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(productId);
        parcel.writeString(name);
        parcel.writeString(model);
        parcel.writeString(category);
        parcel.writeString(style);
        parcel.writeString(color);
        parcel.writeFloat(price);
        parcel.writeInt(quantity);
    }
}
