package virtualpet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import virtualpet.dto.*;
import virtualpet.model.Pet;
import virtualpet.model.User;
import virtualpet.repositories.UserRepository;
import virtualpet.services.PetService;
import virtualpet.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;
    private final UserRepository userRepository;
    private final UserService userService;

  /*  @PostMapping("/newPet")
    public ResponseEntity<Pet> createPet(@RequestBody PetRequest petRequest,
                                         @AuthenticationPrincipal UserDetails userDetails){
        Pet savedPet = petService.createPet(petRequest, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    } */

    @PostMapping("/newPet")
    public ResponseEntity<Pet> createPet(@RequestBody PetRequest petRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🎯 Seguridad activa: " + auth);
        System.out.println("👤 Usuario autenticado: " + auth.getPrincipal());

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Continúa tu lógica normal
        Pet savedPet = petService.createPet(petRequest, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    }


    @GetMapping("/myPet")
    public ResponseEntity<Pet> showMyPet(@AuthenticationPrincipal UserDetails userDetails){
       Pet pet = petService.showMyPet(userDetails);
       return ResponseEntity.ok(pet);
    }

    @GetMapping("/allPets")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Pet>> allPets(){
        List<Pet> petList = petService.allPets();
        return ResponseEntity.ok(petList);
    }

    @PostMapping("/feed")
    public ResponseEntity<Pet> feedPet(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody FeedPetRequest request) {
        User user = userService.userFound(userDetails);
        Pet updatedPet = petService.feedPet(user, request.getFoodId());
        return ResponseEntity.ok(updatedPet);
    }

  /*  @PostMapping("/giveAccessory")
    public ResponseEntity<Pet> giveAccessory(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody AccessoryRequest accessoryRequest){
        User user = userService.userFound(userDetails);
        Pet updatedPet = petService.giveAccessory(user, accessoryRequest.getAccessoryId());
        return ResponseEntity.ok(updatedPet);
    } */

    @PostMapping("/pet/giveAccessory")
    public ResponseEntity<PetDto> giveAccessory(@RequestBody AccessoryRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.userFound(userDetails);
        Pet updatedPet = petService.giveAccessory(user,request.getAccessoryId());

        PetDto petDto = new PetDto(
                (long) updatedPet.getId(),
                updatedPet.getName(),
                updatedPet.getHappiness(),
                updatedPet.getHealth(),
                updatedPet.getHunger(),
                updatedPet.getStrength(),
                updatedPet.getVictories(),
                updatedPet.getDefeats(),
                updatedPet.getWeight()
        );

        return ResponseEntity.ok(petDto);
    }


    @PostMapping("/trainPet")
    public ResponseEntity<Pet> trainPet(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody TrainPetRequest request){
        User user = userService.userFound(userDetails);
        Pet updatedPet = petService.trainPet(user, request.getDurationInSeconds());
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/deletePet")
    public ResponseEntity<Void> deletePet(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.userFound(userDetails);
        petService.deletePet(user);
        return ResponseEntity.noContent().build();
    }
}
