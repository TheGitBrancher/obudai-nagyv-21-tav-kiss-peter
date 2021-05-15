package cookbook.service.transformer;

import cookbook.persistence.entity.Cook;
import cookbook.service.dto.CookDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CookTransformer {

    private final ModelMapper modelMapper = new ModelMapper();

    public CookDto convertToCookDto(Cook cook) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(cook, CookDto.class);
    }
}
