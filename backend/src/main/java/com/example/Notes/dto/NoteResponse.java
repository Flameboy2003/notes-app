package com.example.Notes.dto;

import java.util.Date;

public class NoteResponse {


        private Long id;
        private String text;
        private Date createdAt;
        private Date updateAt;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }

        public Date getUpdateAt() {
                return updateAt;
        }

        public void setUpdateAt(Date updateAt) {
                this.updateAt = updateAt;
        }
}
