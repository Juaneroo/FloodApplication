package com.flood_web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public SnsClient snsClient(){

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIA2HVQ5EUTRYFL5ZPZ",
                "HnAM8DaOwgmNqzb487mH4SCltoOvme1pTBSxsu6Z"
        );

        // Create SNS client
        return SnsClient.builder()
                .region(Region.US_EAST_1)  // Change to your region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

    }

    @Bean
    public ConnectClient connectClient() {

        //arn:aws:connect:us-west-2:703671903527:instance/012e71cd-70db-42ef-840a-3cdb3665ee3f/contact-flow/0825ced0-c31d-4446-8051-73e67fa603bf

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIA2HVQ5EUTRYFL5ZPZ",
                "HnAM8DaOwgmNqzb487mH4SCltoOvme1pTBSxsu6Z"
        );

        return ConnectClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
