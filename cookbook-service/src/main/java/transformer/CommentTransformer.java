package transformer;

import cookbook.persistence.entity.Comment;
import dto.CommentDto;

public class CommentTransformer {

    public static CommentDto getDTO(Comment toTransform) {
        CommentDto dto = new CommentDto();
        dto.setId(toTransform.getId());
        dto.setDescription(toTransform.getDescription());
        dto.setTimestamp(toTransform.getTimestamp());

        return dto;
    }
}
