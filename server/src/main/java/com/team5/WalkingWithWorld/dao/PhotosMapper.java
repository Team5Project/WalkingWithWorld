package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.global.domain.PhotosDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PhotosMapper {
    @Insert("insert into photos (walking_paths_id, img_name, img_path) values (#{walkingPathsId}, #{imgName}, #{imgPath})")
    boolean addPhotos(PhotosDTO photosDTO);

    @Select("select id, reviews_id, walking_paths_id, img_name, img_path from photos where walking_paths_id = #{id} and reviews_id is null")
    List<PhotosDTO> readPhotos(int id);

    // 사진 하나만 가져오기
    @Select("select id, reviews_id, walking_paths_id, img_name, img_path from photos where walking_paths_id = #{id} and reviews_id is null order by id limit 1")
    List<PhotosDTO> readPhoto(int id);

    @Insert("insert into photos (reviews_id, img_name, img_path) values (#{reviewsId}, #{imgName}, #{imgPath})")
    boolean addReviewPhotos(PhotosDTO photosDTO);
    @Select("select id, reviews_id, img_name, img_path from photos where reviews_id = #{reviewsId}")
    List<PhotosDTO> readReviewPhotos(int reviewsId);
}
