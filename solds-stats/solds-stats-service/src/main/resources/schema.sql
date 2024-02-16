drop table if exists tickets_sold;
create table if not exists tickets_sold
(
    id                bigint                      not null,
    buyer             varchar(255)                not null,
    place_number      int                         not null,
    trip_title        varchar(255)                not null,
    route_number      varchar(255)                not null,
    departure_point   varchar(255)                not null,
    destination_point varchar(255)                not null,
    date_time         timestamp without time zone not null,
    price             int                         not null,
    carrier           varchar(255)                not null,
    timestamp         timestamp                   not null,
    constraint pk primary key (id)
);