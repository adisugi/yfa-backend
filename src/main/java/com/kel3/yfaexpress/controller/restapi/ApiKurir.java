package com.kel3.yfaexpress.controller.restapi;

import com.kel3.yfaexpress.model.dto.KurirDto;
import com.kel3.yfaexpress.model.entity.Kurir;
import com.kel3.yfaexpress.repository.KurirRepository;
import com.kel3.yfaexpress.service.KurirService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kurir")
public class ApiKurir {

    private final KurirRepository kurirRepository;
    private final ModelMapper modelMapper;
    private final KurirService kurirService;

    public ApiKurir(KurirRepository kurirRepository, ModelMapper modelMapper, KurirService kurirService) {
        this.kurirRepository = kurirRepository;
        this.modelMapper = modelMapper;
        this.kurirService = kurirService;
    }


    @GetMapping()
    public List<KurirDto> getListKurir() {
        List<Kurir> kurirList = kurirRepository.findAllByIsDeleteEquals(0);
        List<KurirDto> kurirDtos =
                kurirList.stream()
                        .map(kurir -> mapKurirToKurirDto(kurir))
                        .collect(Collectors.toList());
        return kurirDtos;
    }

    private KurirDto mapKurirToKurirDto(Kurir kurir) {
        KurirDto kurirDto = modelMapper.map(kurir, KurirDto.class);
        kurirDto.setIdKurir(kurir.getIdKurir());
        kurirDto.setNamaKurir(kurir.getNamaKurir());
        kurirDto.setNoTelpKurir(kurir.getNoTelpKurir());
        kurirDto.setAlamat(kurir.getAlamat());
        kurirDto.setNik(kurir.getNik());
        kurirDto.setTtl(kurir.getTtl());

        return kurirDto;
    }

    //getmapping saat edit
    @GetMapping("/{id}")
    public KurirDto getKurir(@PathVariable Integer id) {
        Kurir kurir = kurirRepository.findById(id).get();
        KurirDto kurirDto = new KurirDto();
        modelMapper.map(kurir, kurirDto);
        kurirDto.setIdKurir(kurir.getIdKurir());
        kurirDto.setNamaKurir(kurir.getNamaKurir());
        kurirDto.setNoTelpKurir(kurir.getNoTelpKurir());
        kurirDto.setTtl(kurir.getTtl());
        kurirDto.setNik(kurir.getNik());
        kurirDto.setAlamat(kurir.getAlamat());
        return kurirDto;
    }

    //get image java
    @GetMapping("/getFoto/{idKurir}")
    public byte[] getFoto(@PathVariable Integer idKurir) throws IOException {
        Kurir kurir = kurirRepository.findById(idKurir).get();
        String userFolderPath = "D:/img/";
        String pathFile = userFolderPath + kurir.getFile();
        // String pathFile = "D:/img/1.PNG";
        Path paths = Paths.get(pathFile);
        byte[] foto = Files.readAllBytes(paths);
        return foto;
    }

    //get image react
    @GetMapping("/getImage/{idKurir}")
    public String getImage(@PathVariable Integer idKurir) throws IOException {
        Kurir kurir = kurirRepository.findById(idKurir).get();
        String userFolderPath = "D:/img/";
        String pathFile = userFolderPath + kurir.getFile();
        // String pathFile = "D:/img/1.PNG";
        Path paths = Paths.get(pathFile);
        byte[] foto = Files.readAllBytes(paths);
        String img = Base64.getEncoder().encodeToString(foto);
        return img;
    }

    //insert dan edit
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KurirDto editSave(@RequestPart(value = "kurir", required = true) KurirDto kurirDto,
                             @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {

        Kurir kurir = modelMapper.map(kurirDto, Kurir.class);
        kurir.setIdKurir(kurirDto.getIdKurir());
        kurir.setIsDelete(0);
        if (file == null) {
            kurir.setFile(kurirRepository.findByIdKurir(kurirDto.getIdKurir()).getFile());
        } else {
            String userFolderPath = "D:/img/";
            Path path = Paths.get(userFolderPath);
            Path filePath = path.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Upload file with size" + file.getSize() + " with name :  " + file.getOriginalFilename());
            kurir.setFile(file.getOriginalFilename());
        }

        kurir = kurirService.saveKurirMaterDetail(kurir);
        KurirDto kurirDtoDB = mapKurirToKurirDto(kurir);
        return kurirDtoDB;
    }

    //delete
    @PostMapping("/delete")
    public void delete(@RequestBody KurirDto kurirDto) {
        Kurir kurir = modelMapper.map(kurirDto, Kurir.class);
        kurir.setIdKurir(kurirDto.getIdKurir());
        kurir.setIsDelete(1);
        kurirService.saveKurirMaterDetail(kurir);
    }
}