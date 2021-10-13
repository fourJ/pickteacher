package kr.pe.fourj.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.pe.fourj.domain.Review;
import kr.pe.fourj.dto.ReviewDTO;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	ReviewMapper instance = Mappers.getMapper(ReviewMapper.class);
	
	//@Mappping(target="dateTime" express="java(java.time.LocalDateTime.now())")
	Review toEntity(ReviewDTO dto);
	ReviewDTO toDto(Review review);
}
