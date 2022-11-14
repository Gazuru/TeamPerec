package hu.bme.hit.teamperec.service;

import java.util.List;
import java.util.UUID;

import hu.bme.hit.teamperec.data.ComputerSecurityException;
import hu.bme.hit.teamperec.data.dto.CAFFDto;
import hu.bme.hit.teamperec.data.entity.CAFF;
import hu.bme.hit.teamperec.data.repository.CAFFRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CAFFService {

    private final CAFFRepository caffRepository;

    private final UserService userService;

    public List<CAFF> getCaffs() {
        return caffRepository.findAll();
    }

    public List<CAFF> getCaffsOfUser(UUID uploaderId) {
        return caffRepository.findAllByUploaderId(uploaderId);
    }

    public CAFF uploadCaff(CAFFDto caffDto) {
        var caff = toCaffFromDto(new CAFF(), caffDto);
        return caffRepository.save(caff);
    }

    private CAFF toCaffFromDto(CAFF caff, CAFFDto caffDto) {
        var uploader = userService.getUser(caffDto.uploader());

        caff.setUploader(uploader);
        caff.setDescription(caffDto.description());
        caff.setName(caffDto.name());
        caff.setImage(caffDto.image());

        return caff;
    }

    public void deleteCaff(UUID caffId) {
        if (caffRepository.existsById(caffId)) {
            caffRepository.deleteById(caffId);
        }
    }

    public CAFF getCaff(UUID caffId) {
        return caffRepository.findById(caffId).orElseThrow(
                () -> new ComputerSecurityException("CAFF not found by id: " + caffId));
    }

    public CAFF downloadCaff(UUID caffId) {
        // TODO downloading implementation
        return null;
    }

    public CAFF updateCaff(UUID caffId, CAFFDto caffDto) {
        var caff = toCaffFromDto(getCaff(caffId), caffDto);
        return caffRepository.save(caff);
    }
}
