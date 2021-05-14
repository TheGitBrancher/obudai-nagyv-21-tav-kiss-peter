package transformer;

import cookbook.persistence.entity.Cook;
import cookbook.persistence.entity.Recipe;
import dto.CookDto;
import dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CookTransformer {

    private ModelMapper modelMapper;

    public CookDto convertToCookDto(Cook cook) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(cook, CookDto.class);
    }

    public Cook convertDtoToCook(CookDto cookDto) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(cookDto, Cook.class);
    }
}
