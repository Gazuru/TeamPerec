package hu.bme.hit.teamperec.controller;

import java.util.List;
import java.util.UUID;

import hu.bme.hit.teamperec.data.dto.CAFFDto;
import hu.bme.hit.teamperec.data.dto.CommentDto;
import hu.bme.hit.teamperec.data.response.CAFFResponse;
import hu.bme.hit.teamperec.data.response.CommentResponse;
import hu.bme.hit.teamperec.service.CAFFService;
import hu.bme.hit.teamperec.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/caff")
@RequiredArgsConstructor
public class CAFFController {

    private final CAFFService caffService;

    private final CommentService commentService;

    // CAFF functionality

    @GetMapping("/list")
    public ResponseEntity<List<CAFFResponse>> getCaffs(@RequestParam(required = false) String uploader,
                                                       @RequestParam(required = false) String name) {
        return new ResponseEntity<>(caffService.getCaffs(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/caffs")
    public ResponseEntity<List<CAFFResponse>> getCaffsOfUser(@PathVariable UUID userId) {
        return new ResponseEntity<>(caffService.getCaffsOfUser(userId), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<CAFFResponse> uploadCaff(@RequestBody CAFFDto caffDto) {
        return new ResponseEntity<>(caffService.uploadCaff(caffDto), HttpStatus.CREATED);
    }

    @GetMapping("/{caffId}")
    public ResponseEntity<CAFFResponse> getCaff(@PathVariable UUID caffId) {
        return new ResponseEntity<>(caffService.getCaff(caffId), HttpStatus.OK);
    }

    @PatchMapping("/{caffId}")
    public ResponseEntity<CAFFResponse> updateCaff(@PathVariable UUID caffId, @RequestBody CAFFDto caffDto) {
        return new ResponseEntity<>(caffService.updateCaff(caffId, caffDto), HttpStatus.OK);
    }

    @GetMapping("/{caffId}/download")
    public ResponseEntity<CAFFResponse> downloadCaff(@PathVariable UUID caffId) {
        return new ResponseEntity<>(caffService.downloadCaff(caffId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{caffId}")
    public ResponseEntity<Void> deleteCaff(@PathVariable UUID caffId) {
        caffService.deleteCaff(caffId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{caffId}/comment")
    public ResponseEntity<CommentResponse> createComment(@PathVariable UUID caffId,
                                                         @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(caffId, commentDto), HttpStatus.CREATED);
    }
}
