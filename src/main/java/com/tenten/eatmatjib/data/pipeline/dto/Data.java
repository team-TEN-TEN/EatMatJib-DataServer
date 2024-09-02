package com.tenten.eatmatjib.data.pipeline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Data {
    @JsonProperty("OPNSFTEAMCODE")
    private String opnsfteamcode;
    @JsonProperty("MGTNO")
    private String mgtno;
    @JsonProperty("APVPERMYMD")
    private String apvpermymd;
    @JsonProperty("APVCANCELYMD")
    private String apvcancelymd;
    @JsonProperty("TRDSTATEGBN")
    private String trdstategbn;
    @JsonProperty("TRDSTATENM")
    private String trdstatenm;
    @JsonProperty("DTLSTATEGBN")
    private String dtlstategbn;
    @JsonProperty("DTLSTATENM")
    private String dtlstatenm;
    @JsonProperty("DCBYMD")
    private String dcbymd;
    @JsonProperty("CLGSTDT")
    private String clgstdt;
    @JsonProperty("CLGENDDT")
    private String clgenddt;
    @JsonProperty("ROPNYMD")
    private String ropnymd;
    @JsonProperty("SITETEL")
    private String sitetel;
    @JsonProperty("SITEAREA")
    private String sitearea;
    @JsonProperty("SITEPOSTNO")
    private String sitepostno;
    @JsonProperty("SITEWHLADDR")
    private String sitewhladdr;
    @JsonProperty("RDNWHLADDR")
    private String rdnwhladdr;
    @JsonProperty("RDNPOSTNO")
    private String rdnpostno;
    @JsonProperty("BPLCNM")
    private String bplcnm;
    @JsonProperty("LASTMODTS")
    private String lastmodts;
    @JsonProperty("UPDATEGBN")
    private String updategbn;
    @JsonProperty("UPDATEDT")
    private String updatedt;
    @JsonProperty("UPTAENM")
    private String uptaenm;
    @JsonProperty("X")
    private String x;
    @JsonProperty("Y")
    private String y;
    @JsonProperty("SNTUPTAENM")
    private String sntuptaenm;
    @JsonProperty("MANEIPCNT")
    private String maneipcnt;
    @JsonProperty("WMEIPCNT")
    private String wmeipcnt;
    @JsonProperty("TRDPJUBNSENM")
    private String trdpjubnsenm;
    @JsonProperty("LVSENM")
    private String lvsenm;
    @JsonProperty("WTRSPLYFACILSENM")
    private String wtrsplyfacilsenm;
    @JsonProperty("TOTEPNUM")
    private String totepnum;
    @JsonProperty("HOFFEPCNT")
    private String hoffepcnt;
    @JsonProperty("FCTYOWKEPCNT")
    private String fctyowkepcnt;
    @JsonProperty("FCTYSILJOBEPCNT")
    private String fctysiljobepcnt;
    @JsonProperty("FCTYPDTJOBEPCNT")
    private String fctypdtjobepcnt;
    @JsonProperty("BDNGOWNSENM")
    private String bdngownsenm;
    @JsonProperty("ISREAM")
    private String isream;
    @JsonProperty("MONAM")
    private String monam;
    @JsonProperty("MULTUSNUPSOYN")
    private String multusnupsoyn;
    @JsonProperty("FACILTOTSCP")
    private String faciltotscp;
    @JsonProperty("JTUPSOASGNNO")
    private String jtupsoasgnno;
    @JsonProperty("JTUPSOMAINEDF")
    private String jtupsomainedf;
    @JsonProperty("HOMEPAGE")
    private String homepage;

    private int is_updated;

    public void setIsUpdated(int isUpdated) {
        this.is_updated = isUpdated;
    }
}

