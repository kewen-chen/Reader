package org.gdgny.androidfan.reader;

/**
 * Created by Administrator on 2015/7/26.
 */
public class Book {

        private String mName;
        private int mThumbnail;

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }
//book picture
        public int getThumbnail() {
            return mThumbnail;
        }

        public void setThumbnail(int thumbnail) {
            this.mThumbnail = thumbnail;
        }

}
