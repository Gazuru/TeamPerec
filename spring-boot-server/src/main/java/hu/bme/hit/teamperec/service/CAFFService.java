package hu.bme.hit.teamperec.service;

import java.util.*;

import hu.bme.hit.teamperec.data.ComputerSecurityException;
import hu.bme.hit.teamperec.data.dto.CAFFDto;
import hu.bme.hit.teamperec.data.entity.CAFF;
import hu.bme.hit.teamperec.data.entity.Comment;
import hu.bme.hit.teamperec.data.repository.CAFFRepository;
import hu.bme.hit.teamperec.data.response.CAFFResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CAFFService {

    private final CAFFRepository caffRepository;

    private final UserService userService;

    public List<CAFFResponse> getCaffs(String uploaderName, String name) {
        Set<CAFF> uploaderResults = null;
        Set<CAFF> nameResults = null;

        if (!StringUtils.hasText(uploaderName) && !StringUtils.hasText(name)) {
            return caffRepository.findAll().stream().map(this::toResponse).toList();
        }

        if (StringUtils.hasText(uploaderName)) {
            uploaderResults =
                    new HashSet<>(caffRepository.findAllByUploaderUsernameOrderByUploadedAtDesc(uploaderName));
        }
        if (StringUtils.hasText(name)) {
            nameResults = new HashSet<>(caffRepository.findAllByNameOrderByUploadedAtDesc(name));
        }

        return getOneOrIntersection(uploaderResults, nameResults).stream().map(this::toResponse).toList();
    }

    private Set<CAFF> getOneOrIntersection(Set<CAFF> first, Set<CAFF> second) {
        if (CollectionUtils.isEmpty(first) && !CollectionUtils.isEmpty(second)) {
            return second;
        } else if (!CollectionUtils.isEmpty(first) && CollectionUtils.isEmpty(second)) {
            return first;
        } else if (!CollectionUtils.isEmpty(first) && !CollectionUtils.isEmpty(second)) {
            first.retainAll(second);
            return first;
        } else {
            return Collections.emptySet();
        }
    }

    public List<CAFFResponse> getCaffsOfUser(UUID uploaderId) {
        return caffRepository.findAllByUploaderIdOrderByUploadedAtDesc(uploaderId).stream().map(this::toResponse).toList();
    }

    public CAFFResponse uploadCaff(CAFFDto caffDto) {
        var caff = toCaffFromDto(new CAFF(), caffDto);
        return toResponse(caffRepository.save(caff));
    }

    private CAFF toCaffFromDto(CAFF caff, CAFFDto caffDto) {
        var name = caffDto.name();
        var description = caffDto.description();
        var image = caffDto.imageBase64();

        if (Objects.isNull(caff.getUploader())) {
            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            var uploader = userService.getUserByUsername(username);
            caff.setUploader(uploader);
        }
        if (Objects.nonNull(name)) {
            caff.setName(name);
        }
        if (Objects.nonNull(description)) {
            caff.setDescription(caffDto.description());
        }
        if (Objects.nonNull(image)) {
            caff.setImage(caffDto.imageBase64());
        }

        return caff;
    }

    private CAFFResponse toResponse(CAFF caff) {
        return new CAFFResponse(caff.getId(),
                caff.getName(),
                caff.getDescription(),
                caff.getComments().stream().map(Comment::getId).toList(),
                caff.getImage(),
                caff.getUploader().getId(),
                caff.getUploader().getUsername());
    }

    public void deleteCaff(UUID caffId) {
        if (caffRepository.existsById(caffId)) {
            caffRepository.deleteById(caffId);
        }
    }

    public CAFFResponse getCaff(UUID caffId) {
        return toResponse(getCaffById(caffId));
    }

    public CAFF getCaffById(UUID caffId) {
        return caffRepository.findById(caffId).orElseThrow(
                () -> new ComputerSecurityException("CAFF not found by id: " + caffId));
    }

    public CAFFResponse downloadCaff(UUID caffId) {
        // TODO downloading implementation
        return null;
    }

    public CAFFResponse updateCaff(UUID caffId, CAFFDto caffDto) {
        var caff = toCaffFromDto(getCaffById(caffId), caffDto);
        return toResponse(caffRepository.save(caff));
    }
}
