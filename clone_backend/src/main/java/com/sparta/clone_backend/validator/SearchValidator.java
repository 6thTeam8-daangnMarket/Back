package com.sparta.clone_backend.validator;


import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component // 선언하지 않으면 사용할 수 없다!!!!!
@RequiredArgsConstructor
public class SearchValidator {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    //기본 주소 데이터
    public static String DefaultMapData(Optional<String> mapdata) {
        String defaultMapdata = null;
        if (!mapdata.isPresent()) {
            return defaultMapdata = "서울";
        } else {
            return defaultMapdata = String.valueOf(mapdata);
        }
    }

    //기본 filter 데이터
    public static String DefaultFilterData(Optional<String> filtertype) {
        String defaultFilter = null;
        if (!filtertype.isPresent()) {
            return defaultFilter = "";
        } else {
            return defaultFilter = String.valueOf(filtertype);
        }
    }

    //기본 카테고리 데이터
    public static String DefaultCategoryData(Optional<String> category) {
        String defaultCategory = null;
        if (!category.isPresent()) {
            return defaultCategory = "전자제품";
        } else {
            return defaultCategory = String.valueOf(category);
        }
    }

    //기본 검색 데이터
    public static String DefaultSearchData(Optional<String> searchtitle) {
        String defaultSearchTitle = null;
        if (!searchtitle.isPresent()) {
            return defaultSearchTitle = "recent";
        } else {
            return defaultSearchTitle = String.valueOf(searchtitle);
        }
    }

//    // 기본 limit count 갯수(5)개 설정
//    public Long DefaultLimitCount(Long limitcount) {
//        Long defaultLimitCount = null;
//        if (limitcount == null) {
//            return defaultLimitCount = 5L;
//        } else {
//            return defaultLimitCount = limitcount;
//        }
//    }






}