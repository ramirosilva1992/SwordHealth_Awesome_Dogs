import com.swordhealth.api.objects.Breed
import com.swordhealth.database.entities.BreedDTO

fun List<Breed>.asBreedDTOList(page: Int) = map { breed ->
    breed.asBreedDTO().also { if (page > 0) it.page = page }
}

fun Breed.asBreedDTO() = BreedDTO(
    id = id,
    name = name,
    category = category,
    group = group,
    origin = origin,
    temperament = temperament,
    image = image?.url,
    page = 0
)