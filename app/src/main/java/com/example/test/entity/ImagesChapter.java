package com.example.test.entity;

import java.util.List;

public class ImagesChapter {
    private List<images> images;
    public static class images {
        private String imagePage;
        private String imageFile;

        public String getImagePage() {
            return imagePage;
        }

        public void setImagePage(String imagePage) {
            this.imagePage = imagePage;
        }

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }
    }
}
