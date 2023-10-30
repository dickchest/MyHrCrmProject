package com.myhrcrmproject.domain.enums;

public enum ContractType {
    FULL_TIME,
    PART_TIME,
    INTERNSHIP,
    SEASONAL,
    CONTRACT_FOR_SERVICE;
//
//    private final String displayName;
//    ContractType(String displayName) {
//        this.displayName = displayName;
//    }
//
//    public String getDisplayName() {
//        return displayName;
//    }
//
//    public static ContractType fromDisplayName(String displayName) {
//        for (ContractType contractType : ContractType.values()) {
//            if (contractType.displayName.equalsIgnoreCase(displayName)) {
//                return contractType;
//            }
//        }
//        // if display name doesn't match any of enum throw exception
//        throw new NotFoundException("No enum constant with name: " + displayName);
//    }
}
