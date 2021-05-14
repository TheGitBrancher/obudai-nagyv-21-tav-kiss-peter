package transformer;

import cookbook.persistence.entity.User;
import dto.UserDto;

public class UserTransformer {
    public static UserDto getDto(User toTransform) {

        UserDto dto = new UserDto();

        dto.setId(toTransform.getId());
        dto.setUsername(toTransform.getUsername());
        dto.setPassword(toTransform.getPassword());

        return dto;
    }
}
