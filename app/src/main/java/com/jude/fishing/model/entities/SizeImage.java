package com.jude.fishing.model.entities;

public class SizeImage{
        private String smallImage;
        private String largeImage;
        private String originalImage;

        public String getOriginalImage() {
            return originalImage;
        }

        public void setOriginalImage(String originalImage) {
            this.originalImage = originalImage;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getLargeImage() {
            return largeImage;
        }

        public void setLargeImage(String largeImage) {
            this.largeImage = largeImage;
        }

        public SizeImage(String smallImage, String largeImage, String originalImage) {
            this.smallImage = smallImage;
            this.largeImage = largeImage;
            this.originalImage = originalImage;
        }
    }