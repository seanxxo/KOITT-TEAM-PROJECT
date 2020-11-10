package koitt.ratta.doeat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import koitt.ratta.doeat.domain.GalleryLikeVo;


@Mapper
public interface LikeMapper {
	
	@Select("SELECT COUNT(ROWNUM)"
			+ " FROM gallery_like"
			+ " WHERE u_idx=#{uIdx} AND g_idx=#{gIdx}")
	public int isLike(GalleryLikeVo galleryLikeVo);
	
	@Insert("INSERT INTO gallery_like"
			+ " VALUES(g_l_idx_seq.NEXTVAL, #{uIdx}, #{gIdx})")
	public int addLike(GalleryLikeVo galleryLikeVo);
	
	@Select("SELECT like_cnt"
			+ " FROM gallery_list"
			+ " WHERE g_idx=#{gIdx}")
	public int viewLike(int gIdx);

}
