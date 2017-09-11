package com.bgf.shbank.domain.mng.equip.sh05001140;

import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 29/03/2017.
 */

@Component
public class Sh05001140ModelMapper extends CustomMapper<Sh05001140, Sh05001140VO> {

    @Value("${onsemiro.upload.repository.img}")
    private String filePath;

    @Override
    public void mapAtoB(Sh05001140 src, Sh05001140VO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));

        if (src.getTerminalFrontGlass() != null) {
            dest.setTerminalFrontGlassFile(AX5File.of(filePath, src.getTerminalFrontGlass()));
        }

        if (src.getTerminalFront() != null) {
            dest.setTerminalFrontFile(AX5File.of(filePath, src.getTerminalFront()));
        }

        if (src.getTerminalCracks() != null) {
            dest.setTerminalCracksFile(AX5File.of(filePath, src.getTerminalCracks()));
        }

        if (src.getMonitor() != null) {
            dest.setMonitorFile(AX5File.of(filePath, src.getMonitor()));
        }

        if (src.getBnkbPartEntranceCracks() != null) {
            dest.setBnkbPartEntranceCracksFile(AX5File.of(filePath, src.getBnkbPartEntranceCracks()));
        }

        if (src.getCardPartEntranceCracks() != null) {
            dest.setCardPartEntranceCracksFile(AX5File.of(filePath, src.getCardPartEntranceCracks()));
        }

        if (src.getIntercom() != null) {
            dest.setIntercomFile(AX5File.of(filePath, src.getIntercom()));
        }

        if (src.getBoardCradle() != null) {
            dest.setBoardCradleFile(AX5File.of(filePath, src.getBoardCradle()));
        }

        if (src.getBranchFloor() != null) {
            dest.setBranchFloorFile(AX5File.of(filePath, src.getBranchFloor()));
        }

        if (src.getInsideWall() != null) {
            dest.setInsideWallFile(AX5File.of(filePath, src.getInsideWall()));
        }

        if (src.getOutsideWall() != null) {
            dest.setOutsideWallFile(AX5File.of(filePath, src.getOutsideWall()));
        }

        if (src.getBranchGlass() != null) {
            dest.setBranchGlassFile(AX5File.of(filePath, src.getBranchGlass()));
        }

        if (src.getCeiling() != null) {
            dest.setCeilingFile(AX5File.of(filePath, src.getCeiling()));
        }

        if (src.getFluorescentLight() != null) {
            dest.setFluorescentLightFile(AX5File.of(filePath, src.getFluorescentLight()));
        }

        if (src.getStickingBillboard() != null) {
            dest.setStickingBillboardFile(AX5File.of(filePath, src.getStickingBillboard()));
        }

        if (src.getGarbagecan() != null) {
            dest.setGarbagecanFile(AX5File.of(filePath, src.getGarbagecan()));
        }

        if (src.getCoolerHeater() != null) {
            dest.setCoolerHeaterFile(AX5File.of(filePath, src.getCoolerHeater()));
        }

        if (src.getBranchPeriphery() != null) {
            dest.setBranchPeripheryFile(AX5File.of(filePath, src.getBranchPeriphery()));
        }
    }
}