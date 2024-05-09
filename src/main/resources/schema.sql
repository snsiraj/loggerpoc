create table if not exists customer (
    id int primary key not null auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    mobile varchar(255)
);
create table if not exists product (
                                        id int primary key not null auto_increment,
                                        name varchar(255),
                                        model varchar(255),
                                        release_version varchar(255)
);
create table if not exists customer_product (
    id int primary key not null auto_increment,
    customer_id int,
    product_id int,
    foreign key (customer_id) references customer(id),
    foreign key (product_id) references product(id)
);