package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {
    // 获取banner
    List<BannerVO> getBanners();
    // 获取热映
    FilmVO getHotFilms(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取即将上映的影片  受欢迎排序
    FilmVO getSoonFilms(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取经典api
    FilmVO getClassicFilms(int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取票房排行吧
    List<FilmInfo> getBoxRanking();
    // 获取人气排行
    List<FilmInfo> getExpectRanking();
    // 获取top100
    List<FilmInfo> getTop();

    // ==== 获取影片条件接口
    List<CatVO> getCats();
    List<SourceVO> getSources();
    List<YearVO> getYears();

    // ==== 影片查询  和详情
    // 根据影片ID或者名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType,String searchParam);

    // 获取影片描述信息
    FilmDescVO getFilmDesc(String filmId);

    // 获取图片信息
    ImgVO getImgs(String filmId);

    // 获取导演信息
    ActorVO getDectInfo(String filmId);

    // 获取演员信息
    List<ActorVO> getActors(String filmId);
}
