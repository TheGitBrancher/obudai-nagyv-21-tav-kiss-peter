package transformer;

import cookbook.persistence.entity.Cook;
import dto.CookDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

public class CookTransformer {

    private final ModelMapper modelMapper = new ModelMapper();

    public CookDto convertToCookDto(Cook cook) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(cook, CookDto.class);
    }

    public Cook convertDtoToCook(CookDto cookDto) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(cookDto, Cook.class);
    }
}
